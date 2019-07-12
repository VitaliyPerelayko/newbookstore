package com.intexsoft.service.entityservice.impl;

import com.intexsoft.dao.model.Role;
import com.intexsoft.dao.repository.RoleRepository;
import com.intexsoft.service.entityservice.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> findByName(String name) {
        return Optional.ofNullable(roleRepository.findByName(name));
    }
}
