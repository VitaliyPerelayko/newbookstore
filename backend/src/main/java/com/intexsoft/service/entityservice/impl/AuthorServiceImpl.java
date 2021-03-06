package com.intexsoft.service.entityservice.impl;

import com.intexsoft.dao.model.Author;
import com.intexsoft.dao.repository.AuthorRepository;
import com.intexsoft.service.entityservice.AuthorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of service layer for Author entity.
 */
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    /**
     * Find all authors from database.
     *
     * @return List of Author
     */
    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    /**
     * Retrieves a Author by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id
     * @throws RuntimeException if Author none found
     */
    @Override
    public Author findById(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        validate(!author.isPresent(),
                "error.author.id.notExist");
        return author.get();
    }

    /**
     * find author by the given name
     *
     * @param name name of author
     * @return author with the given name
     */
    @Override
    public Optional<Author> findByName(String name) {
        return Optional.ofNullable(authorRepository.findAuthorByName(name));
    }

    /**
     * @param name name of author
     * @return true if author exist in database and false otherwise
     */
    @Override
    public boolean existByName(String name) {
        return authorRepository.existsByName(name);
    }

    /**
     * Save new entity Author.
     *
     * @param author author entity
     * @return saved entity
     */
    @Transactional
    @Override
    public Author save(@Valid Author author) {
        validate(author.getId() != null,
                "error.author.haveId");
        validate(authorRepository.existsByName(author.getName()), "error.author.name.notUnique");
        return authorRepository.saveAndFlush(author);
    }

    /**
     * Save all entities from List
     * (use batching)
     *
     * @param authors List of authors
     * @return List of saved authors
     */
    @Transactional
    @Override
    public List<Author> saveAll(List<@Valid Author> authors) {
        authors.forEach(author -> {
            validate(author.getId() != null, "error.author.haveId");
            validate(authorRepository.existsByName(author.getName()), "error.author.name.notUnique");
        });
        return authorRepository.saveAll(authors);
    }

    /**
     * Save all entities from List
     * (use batching)
     * (this method is used for save imported data)
     *
     * @param authors List of authors
     * @return List of saved authors
     */
    @Transactional
    @Override
    public List<Author> saveBatch(List<@Valid Author> authors) {
        authors.forEach(author -> {
            Optional<Author> updatedAuthor = findByName(author.getName());
            updatedAuthor.ifPresent(value -> author.setId(value.getId()));
        });
        return authorRepository.saveAll(authors);
    }

    /**
     * Update entity Author.
     *
     * @param author author entity
     * @return updated entity
     */
    @Transactional
    @Override
    public Author update(@Valid Author author) {
        Long id = author.getId();
        validate(id == null,
                "error.author.haveNoId");
        isExist(id);
        Optional<Author> duplicate = findByName(author.getName());
        duplicate.ifPresent(value -> validate(id.equals(value.getId()), "error.author.name.notUnique"));
        return authorRepository.saveAndFlush(author);
    }

    /**
     * Deletes a given entity.
     *
     * @param author author entity
     */
    @Override
    @Transactional
    public void delete(@Valid Author author) {
        Long id = author.getId();
        validate(id == null, "error.author.haveId");
        isExist(id);
        authorRepository.delete(author);
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
        isExist(id);
        authorRepository.deleteById(id);
    }

    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }
    }

    private void isExist(Long id) {
        validate(!authorRepository.existsById(id), "error.author.id.notExist");
    }
}
