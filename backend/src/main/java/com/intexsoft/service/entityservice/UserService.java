package com.intexsoft.service.entityservice;

import com.intexsoft.dao.model.Role;
import com.intexsoft.dao.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    boolean existByUsername(String username);

    Optional<User> getUserWithRoles(String username);

    @Transactional
    User save(User user);

    @Transactional
    User update(User user);

    @Transactional
    void deleteById(Long id);
}
