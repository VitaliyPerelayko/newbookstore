package com.intexsoft.service.importdata.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intexsoft.service.importdata.ImportData;
import com.intexsoft.service.importdata.POJOValidator;
import com.intexsoft.service.importdata.pojo.AuthorPOJO;
import com.intexsoft.service.importdata.pojo.BookPOJO;
import com.intexsoft.service.importdata.pojo.InformationFromFile;
import com.intexsoft.service.importdata.pojo.PublisherPOJO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class ImportDataJacksonImpl implements ImportData {

    private final static Logger LOGGER = LogManager.getLogger(ImportDataJacksonImpl.class);
    private final POJOValidator validator;
    @Value("${path.to.data.json}")
    private String path;

    public ImportDataJacksonImpl(POJOValidator validator) {
        this.validator = validator;
    }

    /**
     * import data from json file
     * Entity will not imported if it's invalid
     * (remark: if there are duplicates of authors then will imported only the last of them)
     *
     * @return List of AuthorsPOJO
     */
    @Override
    public List<AuthorPOJO> importAuthors() {
        List<AuthorPOJO> authorList = getInfo().getAuthorsList();
        List<AuthorPOJO> authorPOJOList = validator.validateAndDistinct(authorList);
        LOGGER.info("There ware imported {} authors", authorPOJOList.size());
        return authorPOJOList;
    }

    /**
     * import data from json file
     * Entity will not imported if it's invalid
     * (remark: if there are duplicates of books then will imported only the last of them)
     *
     * @return List of BooksPOJO
     */
    @Override
    public List<BookPOJO> importBooks() {
        List<BookPOJO> bookList = getInfo().getBooksList();
        List<BookPOJO> bookPOJOList = validator.validateAndDistinct(bookList);
        LOGGER.info("There ware imported {} books", bookPOJOList.size());
        return bookPOJOList;
    }

    /**
     * import data from json file
     * Entity will not imported if it's invalid
     * (remark: if there are duplicates of publishers then will imported only the last of them)
     *
     * @return List of PublishersPOJO
     */
    @Override
    public List<PublisherPOJO> importPublishers() {
        List<PublisherPOJO> publisherList = getInfo().getPublishersList();
        List<PublisherPOJO> publisherPOJOList = validator.validateAndDistinct(publisherList);
        LOGGER.info("There ware imported {} publishers", publisherPOJOList.size());
        return publisherPOJOList;
    }

    private InformationFromFile getInfo() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        InformationFromFile informationFromFile;
        try (InputStream stream = new FileInputStream(path)) {
            informationFromFile = mapper.readValue(stream, InformationFromFile.class);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return informationFromFile;
    }
}
