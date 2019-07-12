package com.intexsoft.service.entityservice;

import com.intexsoft.dao.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    boolean existByUsername(String username);

    @Transactional
    User save(User user);

    @Transactional
    User update(User user);

    @Transactional
    void DeleteById(Long id);
}
