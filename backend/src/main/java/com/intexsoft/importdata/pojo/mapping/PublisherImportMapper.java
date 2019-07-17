package com.intexsoft.importdata.pojo.mapping;

import com.intexsoft.dao.model.Publisher;
import com.intexsoft.importdata.pojo.PublisherPOJO;
import org.springframework.stereotype.Service;

@Service
public class PublisherImportMapper {

    public Publisher mapPublisherPOJOToPublisher(PublisherPOJO publisherPOJO){
        return new Publisher(publisherPOJO.getUniqueValue());
    }
}
