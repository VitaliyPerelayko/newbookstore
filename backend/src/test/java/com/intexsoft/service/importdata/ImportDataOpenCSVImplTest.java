package com.intexsoft.service.importdata;

import com.intexsoft.dao.model.Category;
import com.intexsoft.service.importdata.impl.ImportDataOpenCSVImpl;
import com.intexsoft.service.importdata.pojo.AuthorPOJO;
import com.intexsoft.service.importdata.pojo.BookPOJO;
import com.intexsoft.service.importdata.pojo.PublisherPOJO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ImportDataOpenCSVImplTest {
    private final String pathValidPublishers = getClass().getClassLoader().getResource("datafordb/csv/publishers.csv").getPath();
    private final String pathValidAuthors = getClass().getClassLoader().getResource("datafordb/csv/authors.csv").getPath();
    private final String pathValidBooks = getClass().getClassLoader().getResource("datafordb/csv/books.csv").getPath();
    private final String pathInvalidPublishers = getClass().getClassLoader().getResource("datafordb/csv/invalid_publishers.csv").getPath();
    private final String pathInvalidAuthors = getClass().getClassLoader().getResource("datafordb/csv/invalid_authors.csv").getPath();
    private final String pathInvalidBooks = getClass().getClassLoader().getResource("datafordb/csv/invalid_books.csv").getPath();

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

    @InjectMocks
    private ImportDataOpenCSVImpl importer;
    @Mock
    private POJOValidator validator;

    @Test
    public void testImportPublishers() {
        ReflectionTestUtils.setField(importer, "pathToPublishers", pathValidPublishers);
        when(validator.validateAndDistinct(anyList())).thenAnswer(returnsFirstArg());
        assertEquals(importer.importPublishers(), publisherList);
    }

    @Test
    public void testImportAuthors() {
        ReflectionTestUtils.setField(importer, "pathToAuthors", pathValidAuthors);
        when(validator.validateAndDistinct(anyList())).thenAnswer(returnsFirstArg());
        assertEquals(importer.importAuthors(), authorList);
    }

    @Test
    public void testImportBooks() {
        ReflectionTestUtils.setField(importer, "pathToBooks", pathValidBooks);
        when(validator.validateAndDistinct(anyList())).thenAnswer(returnsFirstArg());
        assertEquals(importer.importBooks(), bookList);
    }

    @Test
    public void testImportPublishersInvalidData() {
        ReflectionTestUtils.setField(importer, "pathToPublishers", pathInvalidPublishers);
        when(validator.validateAndDistinct(anyList())).thenAnswer(returnsFirstArg());
        importer.importPublishers().forEach(publisher -> assertSame(publisher.getUniqueValue(), null));
    }

    @Test
    public void testImportAuthorsInvalidData() {
        ReflectionTestUtils.setField(importer, "pathToAuthors", pathInvalidAuthors);
        when(validator.validateAndDistinct(anyList())).thenAnswer(returnsFirstArg());
        List<AuthorPOJO> authors = importer.importAuthors();
        assertSame(authors.get(0).getBirthDate(), null);
        assertEquals(authors.get(2), authorList.get(2));
        assertNotNull(authors.get(3).getUniqueValue());
    }

    @Test
    public void testImportBooksInvalidData() {
        ReflectionTestUtils.setField(importer, "pathToBooks", pathInvalidBooks);
        when(validator.validateAndDistinct(anyList())).thenAnswer(returnsFirstArg());
        List<BookPOJO> books = importer.importBooks();
        assertSame(books.get(0).getPublishDate(), null);
        assertSame(books.get(1).getCategory(), null);
        assertEquals(books.get(2).getPrice(), new BigDecimal("-1.76"));
        assertNotEquals(books.get(3).getAuthors(), bookList.get(3).getAuthors());
        assertSame(books.get(3).getPrice(), null);
    }
}

