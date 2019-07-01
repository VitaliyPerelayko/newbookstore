package com.intexsoft.dao.importdata.impl;

import com.intexsoft.dao.importdata.ImportData;
import com.intexsoft.dao.importdata.pojo.BookPOJO;
import com.intexsoft.dao.importdata.pojo.Data;
import com.intexsoft.dao.model.Author;
import com.intexsoft.dao.model.Book;
import com.intexsoft.dao.model.Publisher;
import com.intexsoft.service.AuthorService;
import com.intexsoft.service.BookService;
import com.intexsoft.service.PublisherService;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImportDataJAXBImpl implements ImportData {

    private final BookService bookService;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    private Data data = unmarshallData();

    public ImportDataJAXBImpl(BookService bookService, AuthorService authorService, PublisherService publisherService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.publisherService = publisherService;
    }

    @Override
    public void saveData() {
        savePublishers();
        saveAuthors();
        saveBooks();
    }

    @Override
    public List<Author> saveAuthors() {
        List<Author> authorList = new ArrayList<>();
        data.getAuthorsList().forEach(authorPOJO -> {
            Author author = new Author();
            author.setName(authorPOJO.getName());
            author.setBio(authorPOJO.getName());
            author.setBirthDate(authorPOJO.getBirthDate());
            authorService.save(author);
            authorList.add(author);
        });
        return authorList;
    }

    @Override
    public List<Book> saveBooks() {
        List<Book> bookList = new ArrayList<>();
        data.getBooksList().forEach(bookPOJO -> {
            Book book = fillBook(bookPOJO);
            bookService.save(book);
            bookList.add(book);
        });
        return bookList;
    }

    @Override
    public List<Publisher> savePublishers() {
        List<Publisher> publisherList = new ArrayList<>();
        data.getPublishersList().forEach(publisherPOJO -> {
            Publisher publisher = new Publisher();
            publisher.setName(publisherPOJO.getName());
            publisherService.save(publisher);
            publisherList.add(publisher);
        });
        return publisherList;
    }

    private Book fillBook(BookPOJO bookPOJO) {
        Book book = new Book();
        book.setName(bookPOJO.getName());
        book.setCode(bookPOJO.getCode());
        book.setPublisher(publisherService.findByName(bookPOJO.getPublisher()));
        book.setCategory(bookPOJO.getCategory());
        book.setPublishDate(bookPOJO.getPublishDate());
        book.setPrice(bookPOJO.getPrice());
        book.setAuthors(bookPOJO.getAuthors().stream().map(authorService::findByName).collect(Collectors.toSet()));
        book.setDescription(bookPOJO.getDescription());
        return book;
    }

    private Data unmarshallData() {
        Data data = null;
        try {
            data = (Data) JAXBContext.newInstance(Data.class).createUnmarshaller().
                    unmarshal(new FileReader(getClass().getResource("/datafordb/data.xml").getPath()));
        } catch (JAXBException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
}
