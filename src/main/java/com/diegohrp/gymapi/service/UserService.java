package com.diegohrp.gymapi.service;

import com.diegohrp.gymapi.aspects.LoggableTransaction;
import com.diegohrp.gymapi.dto.trainee.UpdateTraineeDto;
import com.diegohrp.gymapi.dto.trainer.UpdateTrainerDto;
import com.diegohrp.gymapi.dto.user.LoggedUserDto;
import com.diegohrp.gymapi.dto.user.LoginUserDto;
import com.diegohrp.gymapi.dto.user.UpdateStatusDto;
import com.diegohrp.gymapi.entity.user.User;
import com.diegohrp.gymapi.mapper.UserMapper;
import com.diegohrp.gymapi.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final JwtService jwtService;
    private final LoginAttemptService loginAttemptService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @LoggableTransaction
    @Transactional
    public User create(String firstName, String lastName) {
        User user = new User(firstName, lastName);
        user.setUsername(this.generateUsername(user.getFirstName(), user.getLastName()));
        String plainPassword = this.generatePassword();
        user.setPlainGeneratedPassword(plainPassword);
        user.setPassword(passwordEncoder.encode(plainPassword));
        repository.save(user);
        return user;
    }

    @Transactional
    @LoggableTransaction
    public void update(User user, UpdateTraineeDto traineeDto) {
        mapper.updateUserFromDto(traineeDto, user);
        repository.save(user);
    }

    @Transactional
    @LoggableTransaction
    public void update(User user, UpdateTrainerDto trainerDto) {
        System.out.println(trainerDto.firstName());
        System.out.println(user.getFirstName());
        mapper.updateUserFromDto(trainerDto, user);
        System.out.println(user.getFirstName());
        repository.save(user);
    }

    @Transactional
    @LoggableTransaction
    public void toggleActive(UpdateStatusDto dto) {
        Optional<User> user = repository.findByUsername(dto.username());
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        user.get().setIsActive(dto.isActive());
        repository.save(user.get());

    }

    @Transactional
    @LoggableTransaction
    public Boolean changePassword(String username, String oldPassword, String newPassword) {
        Optional<User> user = repository.findByUsername(username);
        if (user.isPresent() && user.get().getPassword().equals(oldPassword)) {
            user.get().setPassword(newPassword);
            repository.save(user.get());
            return true;
        }
        return false;
    }

    @Transactional
    @LoggableTransaction
    public LoggedUserDto login(LoginUserDto credentials, HttpServletRequest request) {
        System.out.println("Entra a login service");

        String ip = getClientIP(request);
        if (loginAttemptService.isBlocked(ip)) {
            throw new RuntimeException("Too many attepts, this IP is blocked for 5 minutes");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.username(),
                            credentials.password()
                    )
            );
            loginAttemptService.loginSucceeded(ip);
            User user = repository.findByUsername(credentials.username()).orElseThrow();
            String jwt = jwtService.generateToken(user);

            return new LoggedUserDto(user.getUsername(), jwt);


        } catch (BadCredentialsException e) {
            loginAttemptService.loginFailed(ip);
            throw e;
        }
    }


    private Boolean userNameExists(String username) {
        return repository.findByUsername(username).isPresent();
    }

    private String generateUsername(String firstName, String lastName) {
        String baseUsername = firstName + "." + lastName;
        String username = baseUsername;
        int counter = 1;
        while (this.userNameExists(username)) {
            counter++;
            username = baseUsername + counter;
        }

        return username;
    }

    private String generatePassword() {
        StringBuilder sb = new StringBuilder();
        int asciiStart = 33;
        int asciiEnd = 126;
        for (int i = 0; i < 10; i++) {
            char character = (char) Math.floor(Math.random() * (asciiEnd - asciiStart) + asciiStart);
            sb.append(character);
        }
        return sb.toString();
    }

    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
