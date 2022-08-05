package com.load.filter.load_filter_platform.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class UserDto {
    private UUID id;
    private String name;
    private String username;

}
