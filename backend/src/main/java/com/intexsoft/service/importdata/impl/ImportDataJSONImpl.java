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
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImportDataJSONImpl implements ImportData {

    private final static Logger LOGGER = LogManager.getLogger(ImportDataJSONImpl.class);
    private final POJOValidator validator = POJOValidatorImpl.getValidator();


    @Override
    public List<AuthorPOJO> importAuthors(String path) {
        return null;
    }

    @Override
    public List<BookPOJO> importBooks(String path) {
        return null;
    }

    @Override
    public List<PublisherPOJO> importPublishers(String path) {
        List<PublisherPOJO> publisherList = getInfo(path).getPublishersList();

        List<PublisherPOJO> publisherPOJOList = new ArrayList<>(validator.distinct(publisherList));
        LOGGER.info("There ware imported {} publishers", publisherPOJOList.size());
        return publisherPOJOList;
    }

    private InformationFromFile getInfo(String path){
        ObjectMapper mapper = new ObjectMapper();
        InformationFromFile informationFromFile;
        try (InputStream stream = new FileInputStream(path)) {
            informationFromFile = mapper.readValue(stream, InformationFromFile.class);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return informationFromFile;
    }

    public static void main(String[] args) {
        ImportData importData = new ImportDataJSONImpl();

        importData.importPublishers("/home/INTEXSOFT/vitaly.perelaiko/IdeaProjects/firstProject/newbookstore/backend/src/main/resources/datafordb/data.json").
                forEach(publisherPOJO -> System.out.println(publisherPOJO.getUniqueValue()));
    }
}
