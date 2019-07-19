package com.intexsoft.service.entityservice.impl;

import com.intexsoft.dao.model.Author;
import com.intexsoft.dao.model.Book;
import com.intexsoft.dao.repository.AuthorRepository;
import com.intexsoft.service.entityservice.AuthorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
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
     * find authors of the given book
     *
     * @param book book
     * @return list of authors
     */
    @Override
    public List<Author> findAuthorsOfBook(Book book){
       return authorRepository.findAllByBooks(book);
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
    public Author getById(Long id){
        return authorRepository.getOne(id);
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
     * (this method is used for saveAndUpdate imported data)
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
