package com.intexsoft.importdata.impl;

import com.intexsoft.importdata.ImportData;
import com.intexsoft.importdata.POJOValidator;
import com.intexsoft.importdata.pojo.AuthorPOJO;
import com.intexsoft.importdata.pojo.BookPOJO;
import com.intexsoft.importdata.pojo.InformationFromFile;
import com.intexsoft.importdata.pojo.PublisherPOJO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Service for importing data from xml file
 */
@Service
public class ImportDataJAXBImpl implements ImportData {

    private final static Logger LOGGER = LogManager.getLogger(ImportDataJAXBImpl.class);
    private final POJOValidator validator;
    @Value("${path.to.data.xml}")
    private String path;

    public ImportDataJAXBImpl(POJOValidator validator) {
        this.validator = validator;
    }

    /**
     * import data from xml file
     * Entity will not imported if it's invalid
     * (remark: if there are duplicates of authors then will imported only the last of them)
     *
     * @return List of AuthorsPOJO
     */
    @Override
    public List<AuthorPOJO> importAuthors() {
        List<AuthorPOJO> authorList = unmarshalData().getAuthorsList();

        List<AuthorPOJO> authorPOJOList = validator.validateAndDistinct(authorList);
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
    public List<BookPOJO> importBooks() {
        List<BookPOJO> bookList = unmarshalData().getBooksList();

        List<BookPOJO> bookPOJOList = validator.validateAndDistinct(bookList);
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
    public List<PublisherPOJO> importPublishers() {
        List<PublisherPOJO> publisherList = unmarshalData().getPublishersList();

        List<PublisherPOJO> publisherPOJOList = validator.validateAndDistinct(publisherList);
        LOGGER.info("There ware imported {} publishers", publisherPOJOList.size());
        return publisherPOJOList;
    }

    private InformationFromFile unmarshalData() {
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
