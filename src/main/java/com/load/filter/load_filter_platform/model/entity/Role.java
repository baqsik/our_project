package com.load.filter.load_filter_platform.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "role_name", nullable = false)
    private RoleName name;

    private enum RoleName {
        ADMIN,
        USER
    }
}
