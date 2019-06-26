package com.intexsoft.dao.repository;

import com.intexsoft.dao.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
