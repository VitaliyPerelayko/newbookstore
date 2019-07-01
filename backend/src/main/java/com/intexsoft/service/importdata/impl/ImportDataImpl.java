package com.intexsoft.service.importdata.impl;

import com.intexsoft.service.importdata.ImportData;
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
public class ImportDataImpl implements ImportData {

    private final BookService bookService;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    private final Document document = getDocument();


    public ImportDataImpl(BookService bookService, AuthorService authorService, PublisherService publisherService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.publisherService = publisherService;
    }


    /**
     * save data from xml to Database for entity Author
     *
     * @return list of Author
     */
    @Override
    public List<Author> importAuthors() {
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

    /**
     * save data from xml to Database for entity Book
     *
     * @return list of Book
     */
    @Override
    public List<Book> importBooks() {
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

    /**
     * save data from xml to Database for entity Publisher
     *
     * @return list of Publisher
     */
    @Override
    public List<Publisher> importPublishers() {
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

    private Document getDocument() {
        SAXReader reader = new SAXReader();
        Document document;
        try {
            document = reader.read(new File(getClass().getResource("/datafordb/data.xml").getPath()));
        } catch (DocumentException e) {
//            TODO: Handle exception ()
            throw new RuntimeException(e);
        }
        return document;
    }

}
