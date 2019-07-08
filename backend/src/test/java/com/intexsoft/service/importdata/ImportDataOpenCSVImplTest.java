package com.intexsoft.service.importdata;

import com.intexsoft.dao.model.Category;
import com.intexsoft.service.importdata.impl.ImportDataOpenCSVImpl;
import com.intexsoft.service.importdata.pojo.AuthorPOJO;
import com.intexsoft.service.importdata.pojo.BookPOJO;
import com.intexsoft.service.importdata.pojo.PublisherPOJO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ImportDataOpenCSVImplTest {
    private final String pathValidPublishers = getClass().getClassLoader().getResource("datafordb/csv/publishers.csv").getPath();
    private final String pathValidAuthors = getClass().getClassLoader().getResource("datafordb/csv/authors.csv").getPath();
    private final String pathValidBooks = getClass().getClassLoader().getResource("datafordb/csv/books.csv").getPath();
    private final String pathInvalidPublishers = getClass().getClassLoader().getResource("datafordb/csv/invalid_publishers.csv").getPath();
    private final String pathInvalidAuthors = getClass().getClassLoader().getResource("datafordb/csv/invalid_authors.csv").getPath();
    private final String pathInvalidBooks = getClass().getClassLoader().getResource("datafordb/csv/invalid_books.csv").getPath();
    private final String pathDuplicatePublishers = getClass().getClassLoader().getResource("datafordb/csv/duplicate_publishers.csv").getPath();
    private final String pathDuplicateAuthors = getClass().getClassLoader().getResource("datafordb/csv/duplicate_authors.csv").getPath();
    private final String pathDuplicateBooks = getClass().getClassLoader().getResource("datafordb/csv/duplicate_books.csv").getPath();

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
                    Collections.singleton("Ernest Hemingway"), LocalDate.of(1940, 9, 21),
                    "AMG", new BigDecimal("3.99"), Category.DRAMA),
            new BookPOJO("Foundation", "0001", "fantastic",
                    Collections.singleton("Isaac Asimov"), LocalDate.of(1985, 3, 23),
                    "AST", new BigDecimal("5.86"), Category.DETECTIVE),
            new BookPOJO("Anna on the neck", "0002", "funny",
                    Collections.singleton("Anton Chekhov"), LocalDate.of(1962, 1, 1),
                    "AKG", new BigDecimal("1.76"), Category.COMEDY),
            new BookPOJO("Foo", "0003", "foo", Stream.of("Isaac Asimov", "Anton Chekhov").collect(Collectors.toSet()),
                    LocalDate.of(1999, 1, 1),
                    "ABS", new BigDecimal("7.99"), Category.HORROR)
    );

    private ImportDataOpenCSVImpl importData = new ImportDataOpenCSVImpl();

    @Test
    public void testImportPublishers() {
        List<PublisherPOJO> publisherPOJOList = importData.importPublishers(pathValidPublishers);
        assertTrue(publisherList.size() == publisherPOJOList.size() &&
                publisherList.containsAll(publisherPOJOList));
    }

    @Test
    public void testImportAuthors() {
        List<AuthorPOJO> authorPOJOList = importData.importAuthors(pathValidAuthors);
        assertTrue(authorList.size() == authorPOJOList.size() &&
                authorList.containsAll(authorPOJOList));
    }

    @Test
    public void testImportBooks() {
        List<BookPOJO> bookPOJOList = importData.importBooks(pathValidBooks);
        assertTrue(bookList.size() == bookPOJOList.size() &&
                bookList.containsAll(bookPOJOList));
    }

    @Test
    public void testImportPublishersInvalidData() {
        assertEquals(importData.importPublishers(pathInvalidPublishers),
                Collections.singletonList(publisherList.get(0)));
    }

    @Test
    public void testImportAuthorsInvalidData() {
        assertEquals(importData.importAuthors(pathInvalidAuthors),
                Collections.singletonList(authorList.get(2)));
    }

    @Test
    public void testImportBooksInvalidData() {
        assertEquals(importData.importBooks(pathInvalidBooks),
                Collections.singletonList(bookList.get(1)));
    }

    @Test
    public void testImportPublishersDuplicate() {
        List<PublisherPOJO> publisherPOJOList = importData.importPublishers(pathDuplicatePublishers);
        assertTrue(publisherList.size() == publisherPOJOList.size() &&
                publisherList.containsAll(publisherPOJOList));
    }

    @Test
    public void testImportAuthorsDuplicate() {
        assertEquals(importData.importAuthors(pathDuplicateAuthors),
                Collections.singletonList(authorList.get(2)));
    }

    @Test
    public void testImportBooksDuplicate() {
        assertEquals(importData.importBooks(pathDuplicateBooks),
                Collections.singletonList(bookList.get(1)));
    }
}

