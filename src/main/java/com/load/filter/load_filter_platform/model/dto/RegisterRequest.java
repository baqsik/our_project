package com.load.filter.load_filter_platform.model.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    private String name;
    private String username;
    private String password;
    private String roleName;
}
