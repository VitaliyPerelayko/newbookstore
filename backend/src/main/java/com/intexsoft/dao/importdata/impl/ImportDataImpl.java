package com.intexsoft.dao.importdata.impl;

import com.intexsoft.dao.model.Author;
import com.intexsoft.dao.model.Publisher;
import com.intexsoft.service.AuthorService;
import com.intexsoft.service.BookService;
import com.intexsoft.service.PublisherService;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

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


    public void saveData() {
        final Document document = getDocument();

        List<Node> listPublishers = document.selectNodes("/data/publishers/publisher");
        List<Node> listAuthors = document.selectNodes("/data/authors/author");
        List<Node> listBooks = document.selectNodes("/data/books/book");

        listPublishers.forEach(node -> {
            Publisher publisher = new Publisher();
            publisher.setName(node.valueOf("@name"));
            publisherService.save(publisher);
        });

        listPublishers.forEach(node -> {
            Author author = new Author();
            author.setName(node.valueOf("@name"));
            author.setBio(node.valueOf("@bio"));
            author.setBirthDate(LocalDate.parse(node.valueOf("@birthDate")));
        });
    }


    private Document getDocument() {
        SAXReader reader = new SAXReader();
        Document document;
        try {
            document = reader.read(new File(PATH));
        } catch (DocumentException e) {
//            TODO: Handle exception ()
            throw new RuntimeException(e);
        }
        return document;
    }


}
