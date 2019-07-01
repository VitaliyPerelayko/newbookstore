package com.intexsoft.dao.importdata;

import com.intexsoft.dao.importdata.impl.ImportDataJAXBImpl;
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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ImportDataJAXBImplTest {

    @InjectMocks
    private ImportDataJAXBImpl importData;
    @Mock
    private PublisherService publisherService;
    @Mock
    private AuthorService authorService;
    @Mock
    private BookService bookService;

    @Test
    public void testSavePublishers() {
        when(publisherService.save(any(Publisher.class))).thenReturn(new Publisher());
        when(publisherService.existByName(any(String.class))).thenReturn(false);
        assertDoesNotThrow(importData::savePublishers);
    }


    @Test
    public void testSaveAuthors(){
        when(authorService.save(any(Author.class))).thenReturn(new Author());
        when(authorService.existByName(any(String.class))).thenReturn(false);
        assertDoesNotThrow(importData::saveAuthors);
    }

    @Test
    public void testSaveBooks(){
        when(bookService.save(any(Book.class))).thenReturn(new Book());
        when(bookService.existByCode(any(String.class))).thenReturn(false);
        when(publisherService.findByName(any(String.class))).thenReturn(new Publisher());
        when(authorService.findByName(any(String.class))).thenReturn(new Author());
        assertDoesNotThrow(importData::saveBooks);
    }
}
