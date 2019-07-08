package com.intexsoft.service.importdata.impl;

import com.intexsoft.service.importdata.ImportData;
import com.intexsoft.service.importdata.POJOValidator;
import com.intexsoft.service.importdata.pojo.AuthorPOJO;
import com.intexsoft.service.importdata.pojo.BookPOJO;
import com.intexsoft.service.importdata.pojo.InformationFromFile;
import com.intexsoft.service.importdata.pojo.PublisherPOJO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for importing data from xml file
 */
@Service
public class ImportDataJAXBImpl implements ImportData {

    private final static Logger LOGGER = LogManager.getLogger(ImportDataJAXBImpl.class);
    private final POJOValidator validator = POJOValidatorImpl.getValidator();

    /**
     * import data from xml file
     * Entity will not imported if it's invalid
     * (remark: if there are duplicates of authors then will imported only the last of them)
     *
     * @return List of AuthorsPOJO
     */
    @Override
    public List<AuthorPOJO> importAuthors(String path) {
        List<AuthorPOJO> authorList = unmarshalData(path).getAuthorsList();

        List<AuthorPOJO> authorPOJOList = new ArrayList<>(validator.distinct(authorList));
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
        List<BookPOJO> bookList = unmarshalData(path).getBooksList();

        List<BookPOJO> bookPOJOList = new ArrayList<>(validator.distinct(bookList));
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
        List<PublisherPOJO> publisherList = unmarshalData(path).getPublishersList();

        List<PublisherPOJO> publisherPOJOList = new ArrayList<>(validator.distinct(publisherList));
        LOGGER.info("There ware imported {} publishers", publisherPOJOList.size());
        return publisherPOJOList;
    }

    private InformationFromFile unmarshalData(String path) {
        InformationFromFile informationFromFile;
        try (FileReader fileReader = new FileReader(path)) {
            informationFromFile = (InformationFromFile) JAXBContext.newInstance(InformationFromFile.class).createUnmarshaller().
                    unmarshal(fileReader);
        } catch (IOException | JAXBException e) {
            throw new IllegalStateException(e);
        }
        return informationFromFile;
    }
}
