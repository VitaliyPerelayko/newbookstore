package com.intexsoft.dao.repository;

import com.intexsoft.dao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


    /**
     * find user by the given username
     *
     * @param username username of user
     * @return user with the given username
     */
    User findByUsername(String username);

    boolean existsByUsername(String username);
}
