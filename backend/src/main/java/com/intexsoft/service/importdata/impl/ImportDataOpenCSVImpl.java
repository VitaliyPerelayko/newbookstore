package com.intexsoft.service.importdata.impl;

import com.intexsoft.service.importdata.ImportData;
import com.intexsoft.service.importdata.POJOValidator;
import com.intexsoft.service.importdata.pojo.AuthorPOJO;
import com.intexsoft.service.importdata.pojo.BookPOJO;
import com.intexsoft.service.importdata.pojo.ObjectsForBindings;
import com.intexsoft.service.importdata.pojo.PublisherPOJO;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImportDataOpenCSVImpl implements ImportData {

    private final static Logger LOGGER = LogManager.getLogger(ImportDataOpenCSVImpl.class);
    private final POJOValidator validator = POJOValidatorImpl.getValidator();

    @Override
    public List<AuthorPOJO> importAuthors(String path) {
        List<AuthorPOJO> authorList = parseData(path, AuthorPOJO.class);

        List<AuthorPOJO> authorPOJOList = new ArrayList<>(validator.distinct(authorList));
        LOGGER.info("There ware imported {} authors", authorPOJOList.size());
        return authorPOJOList;
    }

    @Override
    public List<BookPOJO> importBooks(String path) {
        List<BookPOJO> bookList = parseData(path, BookPOJO.class);

        List<BookPOJO> bookPOJOList = new ArrayList<>(validator.distinct(bookList));
        LOGGER.info("There ware imported {} books", bookPOJOList.size());
        return bookPOJOList;
    }

    @Override
    public List<PublisherPOJO> importPublishers(String path) {
        List<PublisherPOJO> publisherList = parseData(path, PublisherPOJO.class);

        List<PublisherPOJO> publisherPOJOList = new ArrayList<>(validator.distinct(publisherList));
        LOGGER.info("There ware imported {} books", publisherPOJOList.size());
        return publisherPOJOList;
    }

    private <T extends ObjectsForBindings> List<T> parseData(String path, Class clazz) {
        List<T> objects;
        try (Reader reader = Files.newBufferedReader(Paths.get(path))) {
            CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                    .withType(clazz)
                    .build();
            objects = csvToBean.parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return objects;
    }
}
