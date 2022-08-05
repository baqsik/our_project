package com.load.filter.load_filter_platform.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.load.filter.load_filter_platform.model.dto.LoginDto;
import com.load.filter.load_filter_platform.model.dto.LoginRequest;
import com.load.filter.load_filter_platform.model.dto.RegisterRequest;
import com.load.filter.load_filter_platform.model.dto.UserDto;
import com.load.filter.load_filter_platform.model.entity.User;
import com.load.filter.load_filter_platform.repository.RoleRepository;
import com.load.filter.load_filter_platform.repository.UserRepository;
import com.load.filter.load_filter_platform.service.UserService;
import com.load.filter.load_filter_platform.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    private final RoleRepository roleRepository;

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public UserDto createUser(RegisterRequest registerRequest) {
        User user = new User();
        if (userRepository.findUserByUsername(registerRequest.getUsername()).isEmpty()){
            user.setRole(roleRepository.getRoleByName(registerRequest.getRoleName().toUpperCase()));
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setCreatedAt(LocalDate.now());
            user.setUpdatedAt(LocalDate.now());
            user.setCreatedBy("SUPER");
            user.setUpdatedBy("SUPER");
            user.setSigned(false);
            User dbUser = userRepository.save(user);
            System.out.println(dbUser.getId());

            return new UserDto(dbUser.getId(),dbUser.getName(),dbUser.getUsername());
        }
        throw new HttpClientErrorException.BadRequest();
    }

    @Override
    @Transactional
    public User update(User user, UUID id) {
        user.setId(id);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        userRepository.deleteById(id);
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
