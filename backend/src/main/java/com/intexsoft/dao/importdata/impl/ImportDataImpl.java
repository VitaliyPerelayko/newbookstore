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
    private final Document document = getDocument("/home/INTEXSOFT/vitaly.perelaiko/IdeaProjects/firstProject/newbookstore/backend/src/main/resources/datafordb/data.xml");
    @Value("${fileXML}")
    private String PATH;

    public ImportDataImpl(BookService bookService, AuthorService authorService, PublisherService publisherService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.publisherService = publisherService;
    }

    /**
     * save data from xml to database
     */
    public void saveData() {
        savePublishers();
        saveAuthors();
        saveBooks();
    }


    public List<Author> saveAuthors() {
        List<Node> listAuthors = document.selectNodes("/data/authors/author");
        List<Author> authorList = new ArrayList<>();

        listAuthors.forEach(node -> {
            Author author = new Author();
            author.setName(node.selectSingleNode(".//name").getText());
            author.setBio(node.selectSingleNode(".//bio").getText());
            author.setBirthDate(LocalDate.parse(node.selectSingleNode(".//birthDate").getText()));
            authorService.save(author);
            authorList.add(author);
        });
        return authorList;
    }

    public List<Book> saveBooks() {
        List<Node> listBooks = document.selectNodes("/data/books/book");
        List<Book> bookList = new ArrayList<>();

        listBooks.forEach(node -> {
            Book book = new Book();
            book.setName(node.selectSingleNode(".//title").getText());
            book.setDescription(node.selectSingleNode(".//description").getText());
            book.setAuthors(node.selectNodes(".//authors/name").stream().map(node1 ->
                    authorService.findByName(node1.getText())).
                    collect(Collectors.toSet()));
            book.setPrice(new BigDecimal(node.selectSingleNode(".//price").getText()));
            book.setPublishDate(LocalDate.parse(node.selectSingleNode(".//publishDate").getText()));
            book.setCategory(Category.valueOf(node.selectSingleNode(".//category").getText().toUpperCase()));
            book.setPublisher(publisherService.findByName(node.selectSingleNode(".//publisher").getText()));
            bookService.save(book);
            bookList.add(book);
        });
        return bookList;
    }

    public List<Publisher> savePublishers() {
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

}
