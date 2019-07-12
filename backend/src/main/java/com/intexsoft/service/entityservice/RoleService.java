package com.intexsoft.service.entityservice;

import com.intexsoft.dao.model.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(String name);
}
