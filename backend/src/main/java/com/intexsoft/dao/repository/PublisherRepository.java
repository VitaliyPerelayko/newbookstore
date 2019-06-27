package com.intexsoft.dao.repository;

import com.intexsoft.dao.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher,Long> {

    /**
     * @param name name of publisher
     * @return true if publisher exist in database and false otherwise
     */
    boolean existsByName(String name);

    /**
     * find publisher by the given name
     *
     * @param name name of publisher
     * @return publisher with the given name
     */
    Publisher findPublisherByName(String name);
}
