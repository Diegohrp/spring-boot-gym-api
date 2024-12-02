package com.diegohrp.gymapi.service;

import com.diegohrp.gymapi.aspects.LoggableTransaction;
import com.diegohrp.gymapi.dto.trainee.UpdateTraineeDto;
import com.diegohrp.gymapi.dto.trainer.UpdateTrainerDto;
import com.diegohrp.gymapi.entity.user.User;
import com.diegohrp.gymapi.mapper.UserMapper;
import com.diegohrp.gymapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    @LoggableTransaction
    @Transactional
    public User create(String firstName, String lastName) {
        User user = new User(firstName, lastName);
        user.setUsername(this.generateUsername(user.getFirstName(), user.getLastName()));
        user.setPassword(this.generatePassword());
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
    public void toggleActive(String username, Boolean active) {
        Optional<User> user = repository.findByUsername(username);
        if (user.isEmpty()) {
            //logger.error("The user you want to change status does not exist");
            return;
        }
        user.get().setIsActive(active);
        repository.save(user.get());
        //logger.info("User {} has change their isActive status to: {}", username, active);
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
    public boolean login(String username, String password) {
        Optional<User> user = repository.findByUsername(username);
        return user.isPresent() && user.get().getPassword().equals(password);
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
}
