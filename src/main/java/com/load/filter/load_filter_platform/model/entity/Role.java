package com.load.filter.load_filter_platform.model.entity;

import com.load.filter.load_filter_platform.util.RoleName;
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


}
