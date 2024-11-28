package com.diegohrp.gymapi.controller;

import com.diegohrp.gymapi.dto.user.ChangePasswordDto;
import com.diegohrp.gymapi.dto.user.LoginUserDto;
import com.diegohrp.gymapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final UserService service;

    @GetMapping
    public ResponseEntity<Map<String, Object>> login(@RequestBody @Valid LoginUserDto credentials) {
        Map<String, Object> resp = new HashMap<>();
        if (service.login(credentials.username(), credentials.password())) {
            resp.put("status", HttpStatus.OK.value());
            resp.put("message", "Login successful");
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
        throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Invaid credentials");
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> changePassword(@RequestBody @Valid ChangePasswordDto dto) {
        Map<String, Object> resp = new HashMap<>();
        if (service.changePassword(dto.username(), dto.oldPassword(), dto.newPassword())) {
            resp.put("status", HttpStatus.OK.value());
            resp.put("message", "Password changed successfully");
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
        throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "It wasn't possible to change your password due to wrong credentials");
    }
}
