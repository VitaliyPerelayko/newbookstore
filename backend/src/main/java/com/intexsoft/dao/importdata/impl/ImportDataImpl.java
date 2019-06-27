package com.intexsoft.dao.importdata.impl;

import com.intexsoft.dao.model.Author;
import com.intexsoft.dao.model.Book;
import com.intexsoft.dao.model.Category;
import com.intexsoft.dao.model.Publisher;
import com.intexsoft.service.AuthorService;
import com.intexsoft.service.BookService;
import com.intexsoft.service.PublisherService;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service imports test data from xml file
 */
@Service
public class ImportDataImpl {

    private final BookService bookService;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    @Value("${fileXML}")
    private String PATH;

    public ImportDataImpl(BookService bookService, AuthorService authorService, PublisherService publisherService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.publisherService = publisherService;
    }

    private final Document document = getDocument("/home/INTEXSOFT/vitaly.perelaiko/IdeaProjects/firstProject/newbookstore/backend/src/main/resources/datafordb/data.xml");
    /**
     * save data from xml to database
      */
    public void saveData() {

        savePublishers();

        List<Node> listBooks = document.selectNodes("/data/books/book");


        listBooks.forEach(node -> {
            Book book = new Book();
            book.setName(node.valueOf("@name"));
            book.setDescription(node.valueOf("@description"));
            book.setAuthors(node.selectNodes("/authors/name").stream().map(node1 ->
                    authorService.findByName(node1.getText())).
                    collect(Collectors.toSet()));
            book.setPrice(new BigDecimal(node.valueOf("@price")));
            book.setPublishDate(LocalDate.parse(node.valueOf("@publisher")));
            book.setCategory(Category.valueOf(node.valueOf("@drama").toUpperCase()));
            book.setPublisher(publisherService.findByName(node.valueOf("@publisher")));
            bookService.save(book);
        });
    }

    public List<Author> saveAuthors(){
        List<Node> listAuthors = document.selectNodes("/data/authors/author");
        List<Author> authorList = new ArrayList<>();

        listAuthors.forEach(node -> {
            Author author = new Author();
            author.setName(node.valueOf("@name"));
            author.setBio(node.valueOf("@bio"));
            author.setBirthDate(LocalDate.parse(parseString(node.asXML()).selectSingleNode("/birthDate").getText()));
            authorService.save(author);
            authorList.add(author);
        });
        return authorList;
    }

    public List<Publisher> savePublishers(){
        List<Node> listPublishers = document.selectNodes("/data/publishers/publisher/name");
        List<Publisher> publisherList = new ArrayList<>();

        listPublishers.forEach(node -> {
            Publisher publisher = new Publisher();
            publisher.setName(node.getText());
            publisherService.save(publisher);
            publisherList.add(publisher);
        });
        return publisherList;
    }

    public Document getDocument(String path) {
        SAXReader reader = new SAXReader();
        Document document;
        try {
            document = reader.read(new File(path));
        } catch (DocumentException e) {
//            TODO: Handle exception ()
            throw new RuntimeException(e);
        }
        return document;
    }

    private Document parseString(String text){
        Document document = null;
        try {
            document = DocumentHelper.parseText(text);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }

}
