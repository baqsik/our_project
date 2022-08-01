package com.load.filter.load_filter_platform.controller;

import com.load.filter.load_filter_platform.model.dto.LoginDto;
import com.load.filter.load_filter_platform.model.dto.LoginRequest;
import com.load.filter.load_filter_platform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginDto> login (@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(userService.login(loginRequest));
    }


}

