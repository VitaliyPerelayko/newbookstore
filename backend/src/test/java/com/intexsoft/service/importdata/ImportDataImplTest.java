package com.intexsoft.service.importdata;

import com.intexsoft.service.importdata.impl.ImportDataImpl;
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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void testSavePublishers() {
        Publisher publisher = new Publisher();
        when(publisherService.save(any(Publisher.class))).thenReturn(publisher);
        List<String> publisherNames = importData.importPublishers().stream().map(Publisher::getName).collect(Collectors.toList());
        assertEquals(publisherNames, Arrays.asList("AST", "ABS", "AKG", "AMG"));
        System.out.println(publisherNames);
    }

    @Test
    public void testSaveAuthors() {
        Author author = new Author();
        when(authorService.save(any(Author.class))).thenReturn(author);
        System.out.println(importData.importAuthors().stream().map(Author::getBirthDate).collect(Collectors.toList()));
    }

    @Test
    public void testSaveBooks() {
        Book book = new Book();
        when(bookService.save(any(Book.class))).thenReturn(book);
        System.out.println(importData.importBooks().stream().map(Book::getCategory).collect(Collectors.toList()));
    }

}
