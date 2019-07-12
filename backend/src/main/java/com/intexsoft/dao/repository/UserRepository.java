package com.intexsoft.dao.repository;

import com.intexsoft.dao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("FROM User user JOIN FETCH user.roles WHERE user.username=:username")
    User getUserWithRoles(@Param("username") String username);
}
