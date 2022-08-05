package com.load.filter.load_filter_platform.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserDetails {

    private UUID id;
    private String username;
    private String name;
    private Role role;
}
