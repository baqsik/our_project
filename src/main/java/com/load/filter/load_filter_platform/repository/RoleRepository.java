package com.load.filter.load_filter_platform.repository;

import com.load.filter.load_filter_platform.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role getRoleByName(String admin);
}
