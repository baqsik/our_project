package com.load.filter.load_filter_platform.service;

import com.load.filter.load_filter_platform.model.dto.LoginDto;
import com.load.filter.load_filter_platform.model.dto.LoginRequest;
import com.load.filter.load_filter_platform.model.dto.RegisterRequest;
import com.load.filter.load_filter_platform.model.dto.UserDto;
import com.load.filter.load_filter_platform.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface UserService extends UserDetailsService {
    User getUserById(UUID id);

    @Transactional
    UserDto createUser(RegisterRequest user);

    @Transactional
    User update(User user, UUID id);

    @Transactional
    void delete(UUID id);

    LoginDto login(LoginRequest request);
}
