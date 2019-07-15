package com.intexsoft.importdata.impl;

import com.intexsoft.importdata.ImportData;
import com.intexsoft.importdata.POJOValidator;
import com.intexsoft.importdata.pojo.AuthorPOJO;
import com.intexsoft.importdata.pojo.BookPOJO;
import com.intexsoft.importdata.pojo.ObjectsForBindings;
import com.intexsoft.importdata.pojo.PublisherPOJO;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ImportDataOpenCSVImpl implements ImportData {

    private final static Logger LOGGER = LogManager.getLogger(ImportDataOpenCSVImpl.class);
    private final POJOValidator validator;
    @Value("${path.to.publishers.csv}")
    private String pathToPublishers;
    @Value("${path.to.authors.csv}")
    private String pathToAuthors;
    @Value("${path.to.books.csv}")
    private String pathToBooks;

    public ImportDataOpenCSVImpl(POJOValidator validator) {
        this.validator = validator;
    }

    /**
     * import data from csv file
     * Entity will not imported if it's invalid
     * (remark: if there are duplicates of authors then will imported only the last of them)
     *
     * @return List of AuthorsPOJO
     */
    @Override
    public List<AuthorPOJO> importAuthors() {
        List<AuthorPOJO> authorList = parseData(pathToAuthors, AuthorPOJO.class);

        List<AuthorPOJO> authorPOJOList = validator.validateAndDistinct(authorList);
        LOGGER.info("There ware imported {} authors", authorPOJOList.size());
        return authorPOJOList;
    }

    /**
     * import data from csv file
     * Entity will not imported if it's invalid
     * (remark: if there are duplicates of books then will imported only the last of them)
     *
     * @return List of BooksPOJO
     */
    @Override
    public List<BookPOJO> importBooks() {
        List<BookPOJO> bookList = parseData(pathToBooks, BookPOJO.class);

        List<BookPOJO> bookPOJOList = validator.validateAndDistinct(bookList);
        LOGGER.info("There ware imported {} books", bookPOJOList.size());
        return bookPOJOList;
    }

    /**
     * import data from csv file
     * Entity will not imported if it's invalid
     * (remark: if there are duplicates of publishers then will imported only the last of them)
     *
     * @return List of PublishersPOJO
     */
    @Override
    public List<PublisherPOJO> importPublishers() {
        List<PublisherPOJO> publisherList = parseData(pathToPublishers, PublisherPOJO.class);

        List<PublisherPOJO> publisherPOJOList = validator.validateAndDistinct(publisherList);
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
            throw new IllegalStateException(e);
        }
        return objects;
    }
}
