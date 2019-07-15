package com.intexsoft.importdata;

import com.intexsoft.dao.model.Category;
import com.intexsoft.importdata.impl.ImportDataJAXBImpl;
import com.intexsoft.importdata.pojo.AuthorPOJO;
import com.intexsoft.importdata.pojo.BookPOJO;
import com.intexsoft.importdata.pojo.PublisherPOJO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ImportDataJAXBImplTest {

    private final String pathValidFile = getClass().getClassLoader().getResource("datafordb/xml/valid_data.xml").getPath();
    private final String pathInvalidFile = getClass().getClassLoader().getResource("datafordb/xml/invalid_data.xml").getPath();

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
    private ImportDataJAXBImpl importer;
    @Mock
    private POJOValidator validator;

    @Test
    public void testImportPublishers() {
        setConditions(pathValidFile);
        assertEquals(importer.importPublishers(), publisherList);
    }

    @Test
    public void testImportAuthors() {
        setConditions(pathValidFile);
        assertEquals(importer.importAuthors(), authorList);
    }

    @Test
    public void testImportBooks() {
        setConditions(pathValidFile);
        assertEquals(importer.importBooks(), bookList);
    }

    @Test
    public void testImportPublishersInvalidData() {
        setConditions(pathInvalidFile);
        List<PublisherPOJO> publishers = importer.importPublishers();
        assertSame(publishers.get(1).getUniqueValue(), null);
        assertSame(publishers.get(2).getUniqueValue(), null);
        assertEquals(publishers.get(0), publisherList.get(0));
        assertEquals(3, publishers.size());
    }

    @Test
    public void testImportAuthorsInvalidData() {
        setConditions(pathInvalidFile);
        List<AuthorPOJO> authors = importer.importAuthors();
        assertEquals(authors.size(), 2);
        assertSame(authors.get(0).getBirthDate(), null);
        assertSame(authors.get(1).getBio(), null);
    }

    @Test
    public void testImportBooksInvalidData() {
        setConditions(pathInvalidFile);
        List<BookPOJO> books = importer.importBooks();
        assertEquals(books.size(), 4);
        assertSame(books.get(0).getPublishDate(), null);
        assertSame(books.get(1).getCategory(), null);
        assertSame(books.get(2).getAuthors(), null);
        assertEquals(books.get(3).getAuthors(), Collections.singleton("Anton Chekhov"));
        assertSame(books.get(3).getPrice(), null);
    }

    private void setConditions(String path) {
        ReflectionTestUtils.setField(importer, "path", path);
        when(validator.validateAndDistinct(anyList())).thenAnswer(returnsFirstArg());
    }
}
