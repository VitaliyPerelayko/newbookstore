package com.intexsoft.service.importdata;

import com.intexsoft.dao.model.Category;
import com.intexsoft.service.importdata.impl.ImportDataJAXBImpl;
import com.intexsoft.service.importdata.pojo.AuthorPOJO;
import com.intexsoft.service.importdata.pojo.BookPOJO;
import com.intexsoft.service.importdata.pojo.PublisherPOJO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ImportDataJAXBImplTest {

    private final String pathValidFile = getClass().getClassLoader().getResource("datafordb/valid_data.xml").getPath();
    private final String pathInvalidFile = getClass().getClassLoader().getResource("datafordb/invalid_data.xml").getPath();
    private final String pathFileDuplicate = getClass().getClassLoader().getResource("datafordb/duplicate_data.xml").getPath();

    private final List<AuthorPOJO> authorList = Arrays.asList(
            new AuthorPOJO("Anton Chekhov",
                    "Russian playwright and short-story writer, who is considered to be among the greatest writers of short fiction in history.",
                    LocalDate.parse("1860-01-29")),
            new AuthorPOJO("Ernest Hemingway",
                    "American journalist, novelist, short-story writer, and sportsman",
                    LocalDate.parse("1899-06-21")),
            new AuthorPOJO("Stephen King",
                    "American author of horror, supernatural fiction, suspense, science fiction, and fantasy novels.",
                    LocalDate.parse("1947-09-21")),
            new AuthorPOJO("Isaac Asimov",
                    "American writer and professor of biochemistry at Boston University.",
                    LocalDate.parse("1920-01-02"))
    );
    private final List<PublisherPOJO> publisherList = Arrays.asList(
            new PublisherPOJO("AST"),
            new PublisherPOJO("ABS"),
            new PublisherPOJO("AKG"),
            new PublisherPOJO("AMG")
    );
    private final List<BookPOJO> bookList = Arrays.asList(
            new BookPOJO("For Whom the Bell Tolls", "0000", "about war",
                    Collections.singletonList("Ernest Hemingway"), LocalDate.of(1940, 9, 21),
                    "AMG", new BigDecimal("3.99"), Category.DRAMA),
            new BookPOJO("Foundation", "0001", "fantastic",
                    Collections.singletonList("Isaac Asimov"), LocalDate.of(1985, 3, 23),
                    "AST", new BigDecimal("5.86"), Category.DETECTIVE),
            new BookPOJO("Anna on the neck", "0002", "funny",
                    Collections.singletonList("Anton Chekhov"), LocalDate.of(1962, 1, 1),
                    "AKG", new BigDecimal("1.76"), Category.COMEDY),
            new BookPOJO("Foo", "0003", "foo", Arrays.asList("Isaac Asimov", "Anton Chekhov"),
                    LocalDate.of(1999, 1, 1),
                    "ABS", new BigDecimal("7.99"), Category.HORROR)
    );

    private ImportDataJAXBImpl importData = new ImportDataJAXBImpl();

    @Test
    public void testImportPublishers() {
        List<PublisherPOJO> publisherPOJOList = importData.importPublishers(pathValidFile);
        assertTrue(publisherList.size() == publisherPOJOList.size() &&
                publisherList.containsAll(publisherPOJOList));
    }

    @Test
    public void testImportAuthors() {
        List<AuthorPOJO> authorPOJOList = importData.importAuthors(pathValidFile);
        assertTrue(authorList.size() == authorPOJOList.size() &&
                authorList.containsAll(authorPOJOList));
    }

    @Test
    public void testImportBooks() {
        List<BookPOJO> bookPOJOList = importData.importBooks(pathValidFile);
        assertTrue(bookList.size() == bookPOJOList.size() &&
                bookList.containsAll(bookPOJOList));
    }

    @Test
    public void testImportPublishersInvalidData() {
        assertEquals(importData.importPublishers(pathInvalidFile),
                Collections.singletonList(publisherList.get(0)));
    }

    @Test
    public void testImportAuthorsInvalidData() {
        assertEquals(importData.importAuthors(pathInvalidFile),
                Collections.singletonList(authorList.get(2)));
    }

    @Test
    public void testImportBooksInvalidData() {
        assertEquals(importData.importBooks(pathInvalidFile),
                Collections.singletonList(bookList.get(1)));
    }

    @Test
    public void testImportPublishersDuplicate() {
        List<PublisherPOJO> publisherPOJOList = importData.importPublishers(pathFileDuplicate);
        assertTrue(publisherList.size() == publisherPOJOList.size() &&
                publisherList.containsAll(publisherPOJOList));
    }

    @Test
    public void testImportAuthorsDuplicate() {
        assertEquals(importData.importAuthors(pathFileDuplicate),
                Collections.singletonList(authorList.get(2)));
    }

    @Test
    public void testImportBooksDuplicate() {
        assertEquals(importData.importBooks(pathFileDuplicate),
                Collections.singletonList(bookList.get(1)));
    }
}
