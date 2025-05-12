package com.diegohrp.gymapi.controller;

import com.diegohrp.gymapi.dto.user.ChangePasswordDto;
import com.diegohrp.gymapi.dto.user.LoggedUserDto;
import com.diegohrp.gymapi.dto.user.LoginUserDto;
import com.diegohrp.gymapi.dto.user.UpdateStatusDto;
import com.diegohrp.gymapi.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/login")
    public ResponseEntity<LoggedUserDto> login(@RequestBody @Valid LoginUserDto credentials, HttpServletRequest request) {
        try {
            LoggedUserDto loggedUserDto = service.login(credentials, request);
            return new ResponseEntity<>(loggedUserDto, HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/password")
    public ResponseEntity<Map<String, Object>> changePassword(@RequestBody @Valid ChangePasswordDto dto) {
        Map<String, Object> resp = new HashMap<>();
        if (service.changePassword(dto.username(), dto.oldPassword(), dto.newPassword())) {
            resp.put("status", HttpStatus.OK.value());
            resp.put("message", "Password changed successfully");
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
        throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "It wasn't possible to change your password due to wrong credentials");
    }

    @PatchMapping("/status")
    public ResponseEntity<Map<String, Object>> toogleStatus(@RequestBody @Valid UpdateStatusDto dto) {
        try {
            service.toggleActive(dto);
            Map<String, Object> resp = new HashMap<>();
            resp.put("status", HttpStatus.OK.value());
            resp.put("message", dto.username() + "isActive: " + dto.isActive());
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
