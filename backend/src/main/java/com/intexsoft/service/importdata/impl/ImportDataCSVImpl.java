package com.intexsoft.service.importdata.impl;

import com.intexsoft.service.importdata.ImportData;
import com.intexsoft.service.importdata.pojo.AuthorPOJO;
import com.intexsoft.service.importdata.pojo.BookPOJO;
import com.intexsoft.service.importdata.pojo.ObjectsForXmlBindings;
import com.intexsoft.service.importdata.pojo.PublisherPOJO;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ImportDataCSVImpl implements ImportData {

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
        return getImportedData(Paths.get(path), PublisherPOJO.class);
    }

    private <T extends ObjectsForXmlBindings> List<T> getImportedData(Path path, Class clazz){
        List<T> objects;
        try (Reader reader = Files.newBufferedReader(path)){
            CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                    .withType(clazz)
                    .build();
            objects  = csvToBean.parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return objects;
    }

    public static void main(String[] args) {
        ImportDataCSVImpl importDataCSV = new ImportDataCSVImpl();
        importDataCSV.importPublishers("/home/INTEXSOFT/vitaly.perelaiko/IdeaProjects/firstProject/newbookstore/backend/src/main/resources/datafordb/publishers.csv").
                forEach(publisherPOJO -> System.out.println(publisherPOJO.getUniqueValue()));
    }
}
