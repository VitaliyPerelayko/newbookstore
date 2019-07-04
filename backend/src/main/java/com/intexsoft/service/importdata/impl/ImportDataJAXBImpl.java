package com.intexsoft.service.importdata.impl;

import com.intexsoft.service.importdata.ImportData;
import com.intexsoft.service.importdata.pojo.AuthorPOJO;
import com.intexsoft.service.importdata.pojo.BookPOJO;
import com.intexsoft.service.importdata.pojo.Data;
import com.intexsoft.service.importdata.pojo.PublisherPOJO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Service for importing data from xml file
 */
@Service
public class ImportDataJAXBImpl implements ImportData {

    private final static Logger LOGGER = LogManager.getLogger(ImportDataJAXBImpl.class);
    private final static Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    private final static String WARNINGMESSAGE = " wasn't imported from xml, because of invalid data";
    private final static String INFOMESSAGE = "There ware imported ";

    /**
     * import data from xml file
     * Entity will not imported if it's invalid
     *
     * @return List of AuthorsPOJO
     */
    @Override
    public List<AuthorPOJO> importAuthors(String path) {
        List<AuthorPOJO> authorList = unmarshallData(path).getAuthorsList();
        Iterator<AuthorPOJO> iterator = authorList.iterator();
        while (iterator.hasNext()) {
            AuthorPOJO authorPOJO = iterator.next();
            if (valid(authorPOJO).get()) {
                LOGGER.warn("Entity Author with name " + authorPOJO.getName() + WARNINGMESSAGE);
                iterator.remove();
            }
        }
        authorList = filterDuplicateAuthors(authorList);
        LOGGER.info(INFOMESSAGE + authorList.size() + " authors");
        return filterDuplicateAuthors(authorList);
    }

    /**
     * import data from xml file
     * Entity will not imported if it's invalid
     *
     * @return List of BooksPOJO
     */
    @Override
    public List<BookPOJO> importBooks(String path) {
        List<BookPOJO> bookList = unmarshallData(path).getBooksList();
        Iterator<BookPOJO> iterator = bookList.iterator();
        while (iterator.hasNext()) {
            BookPOJO bookPOJO = iterator.next();
            if (valid(bookPOJO).get()) {
                LOGGER.warn("Entity Book with code " + bookPOJO.getCode() + WARNINGMESSAGE);
                iterator.remove();
            }
        }
        bookList = filterDuplicateBooks(bookList);
        LOGGER.info(INFOMESSAGE + bookList.size() + " books");
        return bookList;
    }

    /**
     * import data from xml file
     * Entity will not imported if it's invalid
     *
     * @return List of PublishersPOJO
     */
    @Override
    public List<PublisherPOJO> importPublishers(String path) {
        List<PublisherPOJO> publisherList = unmarshallData(path).getPublishersList();
        Iterator<PublisherPOJO> iterator = publisherList.iterator();

        while (iterator.hasNext()) {
            PublisherPOJO publisherPOJO = iterator.next();
            if (valid(publisherPOJO).get()) {
                LOGGER.warn("Entity publisher with name " + publisherPOJO.getName() + WARNINGMESSAGE);
                iterator.remove();
            }
        }
        publisherList = filterDuplicatePublishers(publisherList);
        LOGGER.info(INFOMESSAGE + publisherList.size() + " publishers");
        return publisherList;
    }

    private List<PublisherPOJO> filterDuplicatePublishers(List<PublisherPOJO> list) {
        List<PublisherPOJO> newList = new ArrayList<>();
        list.forEach(publisherPOJO -> {
            if (newList.stream().noneMatch(newListPublisher -> publisherPOJO.getName().equals(newListPublisher.getName()))) {
                newList.add(publisherPOJO);
            }
        });
        return newList;
    }

    private List<AuthorPOJO> filterDuplicateAuthors(List<AuthorPOJO> list) {
        List<AuthorPOJO> newList = new ArrayList<>();
        list.forEach(authorPOJO -> {
            if (newList.stream().noneMatch(newListAuthor -> authorPOJO.getName().equals(newListAuthor.getName()))) {
                newList.add(authorPOJO);
            }
        });
        return newList;
    }

    private List<BookPOJO> filterDuplicateBooks(List<BookPOJO> list) {
        List<BookPOJO> newList = new ArrayList<>();
        list.forEach(bookPOJO -> {
            if (newList.stream().noneMatch(newListBook -> bookPOJO.getCode().equals(newListBook.getCode()))) {
                newList.add(bookPOJO);
            }
        });
        return newList;
    }

    private <T> AtomicBoolean valid(T object) {
        AtomicBoolean fail = new AtomicBoolean(false);
        VALIDATOR.validate(object).forEach(violation -> {
            LOGGER.error(violation.getMessage());
            fail.set(true);
        });
        return fail;
    }

    private Data unmarshallData(String path) {
        Data data;
        try {
            data = (Data) JAXBContext.newInstance(Data.class).createUnmarshaller().
                    unmarshal(new FileReader(path));
        } catch (JAXBException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
}
