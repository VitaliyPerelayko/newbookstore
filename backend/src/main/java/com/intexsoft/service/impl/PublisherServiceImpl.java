package com.intexsoft.service.impl;

import com.intexsoft.dao.model.Publisher;
import com.intexsoft.dao.repository.PublisherRepository;
import com.intexsoft.service.PublisherService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of service layer for Publisher entity.
 */
@Service
public class PublisherServiceImpl implements PublisherService {

    private final static Logger LOGGER = LogManager.getLogger(AuthorServiceImpl.class);
    private final PublisherRepository publisherRepository;

    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    /**
     * Find all publishers from database.
     *
     * @return List of Publisher
     */
    @Override
    public List<Publisher> findAll() {
        return publisherRepository.findAll();
    }

    /**
     * Retrieves a Publisher by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id
     * @throws RuntimeException if Publisher none found
     */
    @Override
    public Publisher findById(Long id) {
        Optional<Publisher> publisher = publisherRepository.findById(id);
        validate(!publisher.isPresent(),
                "error.publisher.id.notExist");
        return publisher.get();
    }

    /**
     * find publisher by the given name
     *
     * @param name name of publisher
     * @return publisher with the given name
     */
    @Override
    public Publisher findByName(String name) {
        validate(!publisherRepository.existsByName(name), "error.publisher.name.notExist");
        return publisherRepository.findPublisherByName(name);
    }

    /**
     * @param name name of publisher
     * @return true if publisher exist in database and false otherwise
     */
    @Override
    public boolean existByName(String name) {
        return publisherRepository.existsByName(name);
    }

    /**
     * Save new entity Publisher.
     *
     * @param publisher publisher entity
     * @return saved entity
     */
    @Transactional
    @Override
    public Publisher save(Publisher publisher) {
        validate(publisher.getId() != null,
                "error.publisher.haveId");
        validate(publisherRepository.existsByName(publisher.getName()), "error.publisher.name.notUnique");
        return publisherRepository.saveAndFlush(publisher);
    }

    /**
     * Save all entities from List
     * (use batching)
     *
     * @param publishers List of publishers
     * @return List of saved publishers
     */
    @Transactional
    @Override
    public List<Publisher> saveAll(List<Publisher> publishers) {
        publishers.forEach(publisher -> {
            validate(publisher.getId() != null, "error.publisher.haveId");
            validate(publisherRepository.existsByName(publisher.getName()), "error.publisher.name.notUnique");
        });
        return publisherRepository.saveAll(publishers);
    }

    /**
     * Update entity Publisher.
     *
     * @param publisher publisher entity
     * @return updated entity
     */
    @Transactional
    @Override
    public Publisher update(Publisher publisher) {
        Long id = publisher.getId();
        validate(id == null,
                "error.publisher.haveNoId");
        isExist(id);
        Publisher duplicate = findByName(publisher.getName());
        validate(id.equals(duplicate.getId()), "error.publisher.name.notUnique");
        return publisherRepository.saveAndFlush(publisher);
    }

    /**
     * Deletes a given entity.
     *
     * @param publisher publisher entity
     */
    @Override
    @Transactional
    public void delete(Publisher publisher) {
        Long id = publisher.getId();
        validate(id == null, "error.publisher.haveId");
        isExist(id);
        publisherRepository.delete(publisher);
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
        publisherRepository.deleteById(id);
    }

    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            RuntimeException e = new RuntimeException(errorMessage);
            LOGGER.error(e);
            throw e;
        }
    }

    private void isExist(Long id) {
        validate(!publisherRepository.existsById(id), "error.publisher.id.notExist");
    }
}
