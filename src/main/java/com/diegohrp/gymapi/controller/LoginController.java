package com.diegohrp.gymapi.controller;

import com.diegohrp.gymapi.dto.user.LoginUserDto;
import com.diegohrp.gymapi.entity.user.User;
import com.diegohrp.gymapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final UserService service;

    @GetMapping
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginUserDto credentials) {
        Map<String, Object> resp = new HashMap<>();

        if (service.login(credentials.username(), credentials.password())) {
            resp.put("status", HttpStatus.OK.value());
            resp.put("message", "Login successful");
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }

        resp.put("status", HttpStatus.BAD_REQUEST.value());
        resp.put("error", "Unauthorized");
        resp.put("message", "Invaid credentials");
        return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
    }
}
