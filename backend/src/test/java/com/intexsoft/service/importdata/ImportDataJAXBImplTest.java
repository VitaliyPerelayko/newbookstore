package com.intexsoft.service.importdata;

import com.intexsoft.service.importdata.impl.ImportDataJAXBImpl;
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
        when(publisherService.existByName(any(String.class))).thenReturn(false);
        assertDoesNotThrow(importData::importPublishers);
    }


    @Test
    public void testSaveAuthors(){
        when(authorService.existByName(any(String.class))).thenReturn(false);
        assertDoesNotThrow(importData::importAuthors);
    }

    @Test
    public void testSaveBooks(){
        when(bookService.existByCode(any(String.class))).thenReturn(false);
        when(publisherService.findByName(any(String.class))).thenReturn(new Publisher());
        when(authorService.findByName(any(String.class))).thenReturn(new Author());
        assertDoesNotThrow(importData::importBooks);
    }
}
