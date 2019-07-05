package com.intexsoft.service.enyityservice;

import com.intexsoft.dao.model.Publisher;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PublisherService {
    List<Publisher> findAll();

    Publisher findById(Long id);

    Optional<Publisher> findByName(String name);

    boolean existByName(String name);

    Publisher save(Publisher publisher);

    @Transactional
    List<Publisher> saveAll(List<Publisher> publishers);

    @Transactional
    List<Publisher> saveBatch(List<Publisher> publishers);

    Publisher update(Publisher publisher);

    void delete(Publisher publisher);

    void deleteById(Long id);
}
