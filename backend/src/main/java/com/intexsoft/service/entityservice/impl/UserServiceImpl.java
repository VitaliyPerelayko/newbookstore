package com.intexsoft.service.entityservice.impl;

import com.intexsoft.dao.model.User;
import com.intexsoft.dao.repository.UserRepository;
import com.intexsoft.service.entityservice.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Find all users from database.
     *
     * @return List of Users
     */
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Retrieves an User by its id.
     *
     * @param id id must not be {@literal null}.
     * @return the Optional<User>
     */
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * I use this method in controllers: I set reference to the entity instead of real entity, when map RequestDTO
     * object to real object.
     *
     * Returns a reference to the entity with the given identifier. Depending on how the JPA persistence provider is
     * implemented this is very likely to always return an instance and throw an
     * {@link javax.persistence.EntityNotFoundException} on first access. Some of them will reject invalid identifiers
     * immediately.
     *
     * @param id must not be {@literal null}.
     * @return a reference to the entity with the given identifier.
     * @see EntityManager#getReference(Class, Object) for details on when an exception is thrown.
     */
    @Override
    public User getOne(Long id){
        return userRepository.getOne(id);
    }

    /**
     * Retrieves an User by its username.
     *
     * @param username must not be {@literal null}.
     * @return the Optional<User>
     */
    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    /**
     * @param username username
     * @return true if user exist in database and false otherwise
     */
    @Override
    public boolean existByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * get all user's roles by his id
     *
     * @param username username of User
     * @return Set of Role
     */
    @Override
    public Optional<User> getUserWithRoles(String username) {
        return Optional.ofNullable(userRepository.getUserWithRoles(username));
    }

    /**
     * Save new entity User.
     *
     * @param user user entity
     * @return saved entity
     */
    @Override
    @Transactional
    public User save(@Valid User user) {
        validate(user.getId() != null, "Error. The saved user must hasn't id");
        return userRepository.save(user);
    }

    /**
     * Update entity User.
     *
     * @param user user entity
     * @return updated entity
     */
    @Override
    @Transactional
    public User update(@Valid User user) {
        validate(user.getId() == null, "Error. The saved user must has id");
        return userRepository.save(user);
    }

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }
    }
}
