package com.intexsoft.service.importdata.impl;

import com.intexsoft.service.importdata.ImportData;
import com.intexsoft.service.importdata.pojo.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

/**
 * Service for importing data from xml file
 */
@Service
public class ImportDataJAXBImpl implements ImportData {

    private final static Logger LOGGER = LogManager.getLogger(ImportDataJAXBImpl.class);
    private final static Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * import data from xml file
     * Entity will not imported if it's invalid
     * (remark: if there are duplicates of authors then will imported only the last of them)
     *
     * @return List of AuthorsPOJO
     */
    @Override
    public List<AuthorPOJO> importAuthors(String path) {
        List<AuthorPOJO> authorList = unmarshallData(path).getAuthorsList();

        List<AuthorPOJO> authorPOJOList = new ArrayList<>(getImportedData(authorList));
        LOGGER.info("There ware imported {} authors", authorPOJOList.size());
        return authorPOJOList;
    }

    /**
     * import data from xml file
     * Entity will not imported if it's invalid
     * (remark: if there are duplicates of books then will imported only the last of them)
     *
     * @return List of BooksPOJO
     */
    @Override
    public List<BookPOJO> importBooks(String path) {
        List<BookPOJO> bookList = unmarshallData(path).getBooksList();

        List<BookPOJO> bookPOJOList = new ArrayList<>(getImportedData(bookList));
        LOGGER.info("There ware imported {} books", bookPOJOList.size());
        return bookPOJOList;
    }

    /**
     * import data from xml file
     * Entity will not imported if it's invalid
     * (remark: if there are duplicates of publishers then will imported only the last of them)
     *
     * @return List of PublishersPOJO
     */
    @Override
    public List<PublisherPOJO> importPublishers(String path) {
        List<PublisherPOJO> publisherList = unmarshallData(path).getPublishersList();

        List<PublisherPOJO> publisherPOJOList = new ArrayList<>(getImportedData(publisherList));
        LOGGER.info("There ware imported {} publishers", publisherPOJOList.size());
        return publisherPOJOList;
    }


    private <T extends ObjectsForXmlBindings> Collection<T> getImportedData(List<T> list) {
        Stream<T> stream = list.stream().filter(this::valid);
        final HashMap<String, T> uniqueValues = new HashMap<>();
        stream.forEach(value -> uniqueValues.put(value.getUniqueValue(), value));
        return uniqueValues.values();
    }

    private <T extends ObjectsForXmlBindings> boolean valid(T object) {
        Set<ConstraintViolation<T>> violationSet = VALIDATOR.validate(object);
        if (violationSet.isEmpty()) {
            return true;
        } else {
            violationSet.forEach(LOGGER::error);
            LOGGER.warn("Entity {} with name or code \"{}\" wasn't imported from xml, because of invalid data",
                    object.getClass().getSimpleName(), object.getUniqueValue());
        }
        return false;
    }

    private InformationFromFile unmarshallData(String path) {
        InformationFromFile informationFromFile;
        try (FileReader fileReader = new FileReader(path)) {
            informationFromFile = (InformationFromFile) JAXBContext.newInstance(InformationFromFile.class).createUnmarshaller().
                    unmarshal(fileReader);
        } catch (IOException | JAXBException e) {
            throw new RuntimeException(e);
        }
        return informationFromFile;
    }
}
