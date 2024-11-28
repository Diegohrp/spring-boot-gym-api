package com.diegohrp.gymapi.service;

import com.diegohrp.gymapi.aspects.LoggableTransaction;
import com.diegohrp.gymapi.dto.user.UpdateUserDto;
import com.diegohrp.gymapi.entity.user.User;
import com.diegohrp.gymapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public User create(String firstName, String lastName) {
        User user = new User(firstName, lastName);
        user.setUsername(this.generateUsername(user.getFirstName(), user.getLastName()));
        user.setPassword(this.generatePassword());
        repository.save(user);
        return user;
    }

    public void updateUser(User user, UpdateUserDto userDto) {
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setPassword(userDto.newPassword());
        user.setIsActive(userDto.isActive());
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
