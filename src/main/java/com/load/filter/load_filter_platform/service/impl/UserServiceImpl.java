package com.load.filter.load_filter_platform.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.load.filter.load_filter_platform.model.dto.*;
import com.load.filter.load_filter_platform.model.entity.User;
import com.load.filter.load_filter_platform.repository.RoleRepository;
import com.load.filter.load_filter_platform.repository.UserRepository;
import com.load.filter.load_filter_platform.service.UserService;
import com.load.filter.load_filter_platform.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    private final RoleRepository roleRepository;

    @Override
    public UserDto getUserById(UUID id) {
        return objectMapper.convertValue(userRepository.findById(id).orElseThrow(EntityNotFoundException::new), UserDto.class);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public UserDto createUser(RegisterRequest registerRequest) {
//        TODO: write code if user with that username is exist
        User user = new User();

        if (userRepository.findUserByUsername(registerRequest.getUsername()).isEmpty()){
            user.setRole(roleRepository.getRoleByName(registerRequest.getRoleName().toUpperCase()));
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setSigned(false);
            User dbUser = userRepository.save(user);
            System.out.println(dbUser.getId());

            return new UserDto(dbUser.getId(),dbUser.getName(),dbUser.getUsername());
        }
        return new EntityExistsException("Username is exist");
    }

    @Override
    @Transactional
    public UserDto update(UpdateRequest request, UUID id) {
//        TODO: add code if there is no user with that id
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        user.setPassword(passwordEncoder.encode(nonNull(request.getPassword()) ? request.getPassword() : user.getPassword()));
        user.setName(passwordEncoder.encode(nonNull(request.getName()) ? request.getName() : user.getName()));
        user.setUsername(passwordEncoder.encode(nonNull(request.getUsername()) ? request.getUsername() : user.getUsername()));
        return objectMapper.convertValue(userRepository.save(user), UserDto.class);
    }

    @Override
    @Transactional
    public UserDto delete(UUID id) {
//        TODO: add code if there is no user with that id
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        userRepository.deleteById(id);
        return objectMapper.convertValue(user, UserDto.class);
    }

    @Override
    public LoginDto login(LoginRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println(authentication.getCredentials());
        return new LoginDto(jwtTokenProvider.generateToken(loadUserByUsername(request.getUsername())));
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
