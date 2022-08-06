package com.load.filter.load_filter_platform.service;

import com.load.filter.load_filter_platform.model.dto.*;
import com.load.filter.load_filter_platform.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface UserService extends UserDetailsService {
    UserDto getUserById(UUID id);

    List<User> getAll();

    @Transactional
    UserDto createUser(RegisterRequest user);

    @Transactional
    UserDto update(UpdateRequest user, UUID id);

    @Transactional
    UserDto delete(UUID id);

    LoginDto login(LoginRequest request);
}
