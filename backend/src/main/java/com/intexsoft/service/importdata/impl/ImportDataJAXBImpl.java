package com.intexsoft.service.importdata.impl;

import com.intexsoft.service.importdata.ImportData;
import com.intexsoft.service.importdata.pojo.BookPOJO;
import com.intexsoft.service.importdata.pojo.Data;
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

/**
 * Service for importing data from xml file
 */
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

    /**
     * import data from xml file
     *
     * @return List of Authors
     */
    @Override
    public List<Author> importAuthors() {
        List<Author> authorList = new ArrayList<>();
        data.getAuthorsList().forEach(authorPOJO -> {
            if (authorService.existByName(authorPOJO.getName())){
              return;
            }
            Author author = new Author();
            author.setName(authorPOJO.getName());
            author.setBio(authorPOJO.getBio());
            author.setBirthDate(authorPOJO.getBirthDate());
            authorList.add(author);
        });
        return authorList;
    }

    /**
     * import data from xml file
     *
     * @return List of Books
     */
    @Override
    public List<Book> importBooks() {
        List<Book> bookList = new ArrayList<>();
        data.getBooksList().forEach(bookPOJO -> {
            if (bookService.existByCode(bookPOJO.getCode())){
                return;
            }
            Book book = fillBook(bookPOJO);
            bookList.add(book);
        });
        return bookList;
    }

    /**
     * import data from xml file
     *
     * @return List of Publishers
     */
    @Override
    public List<Publisher> importPublishers() {
        List<Publisher> publisherList = new ArrayList<>();
        data.getPublishersList().forEach(publisherPOJO -> {
            if (publisherService.existByName(publisherPOJO.getName())){
                return;
            }
            Publisher publisher = new Publisher();
            publisher.setName(publisherPOJO.getName());
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
        Data data;
        try {
            data = (Data) JAXBContext.newInstance(Data.class).createUnmarshaller().
                    unmarshal(new FileReader(getClass().getResource("/datafordb/data.xml").getPath()));
        } catch (JAXBException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
}
