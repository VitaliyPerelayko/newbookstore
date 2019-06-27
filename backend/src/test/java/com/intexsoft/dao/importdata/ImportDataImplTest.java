package com.intexsoft.dao.importdata;

import com.intexsoft.dao.importdata.impl.ImportDataImpl;
import com.intexsoft.dao.model.Author;
import com.intexsoft.dao.model.Book;
import com.intexsoft.dao.model.Publisher;
import com.intexsoft.service.AuthorService;
import com.intexsoft.service.BookService;
import com.intexsoft.service.PublisherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ImportDataImplTest {

    @InjectMocks
    private ImportDataImpl importData;

    @Mock
    private PublisherService publisherService;

    @Mock
    private AuthorService authorService;

    @Mock
    private BookService bookService;

    @Test
    public void testSavePublishers(){
        Publisher publisher = new Publisher();
        when(publisherService.save(any(Publisher.class))).thenReturn(publisher);
        System.out.println(importData.savePublishers().stream().map(Publisher::getName).collect(Collectors.toList()));
    }

    @Test
    public void testSaveAuthors(){
        Author author = new Author();
        when(authorService.save(any(Author.class))).thenReturn(author);
        System.out.println(importData.saveAuthors().stream().map(Author::getBirthDate).collect(Collectors.toList()));
    }

    @Test
    public void testSaveBooks(){
        Book book = new Book();
        when(bookService.save(any(Book.class))).thenReturn(book);
        System.out.println(importData.saveBooks().stream().map(Book::getCategory).collect(Collectors.toList()));
    }

}
