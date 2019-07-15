package com.intexsoft.importdata;

import com.intexsoft.dao.model.Category;
import com.intexsoft.importdata.impl.POJOValidatorImpl;
import com.intexsoft.importdata.pojo.AuthorPOJO;
import com.intexsoft.importdata.pojo.BookPOJO;
import com.intexsoft.importdata.pojo.PublisherPOJO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class POJOValidatorImplTest {

    // Valid data
    private final List<AuthorPOJO> validAuthorList = Arrays.asList(
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
    private final List<PublisherPOJO> validPublisherList = Arrays.asList(
            new PublisherPOJO("AST"),
            new PublisherPOJO("ABS"),
            new PublisherPOJO("AKG"),
            new PublisherPOJO("AMG")
    );
    private final List<BookPOJO> validBookList = Arrays.asList(
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
    // Invalid and duplicate data
    private final List<AuthorPOJO> invalidAuthorList = Arrays.asList(
            new AuthorPOJO("Anton Chekhov",
                    "Russian playwright and short-story writer, who is considered to be among the greatest writers of short fiction in history.",
                    null),
            new AuthorPOJO("Ernest Hemingway",
                    "American journalist, novelist, short-story writer, and sportsman",
                    LocalDate.parse("1899-06-21")),
            new AuthorPOJO("   ",
                    "American author of horror, supernatural fiction, suspense, science fiction, and fantasy novels.",
                    LocalDate.parse("1947-09-21")),
            new AuthorPOJO("Isaac Asimov 00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
                    "American writer and professor of biochemistry at Boston University.",
                    LocalDate.parse("1920-01-02")),
            new AuthorPOJO("Ernest Hemingway",
                    "American journalist, novelist, short-story writer, and sportsman",
                    LocalDate.parse("2020-06-21")),
            new AuthorPOJO("Ernest Hemingway",
                    "American journalist, novelist, short-story writer, and sportsman",
                    LocalDate.parse("1899-06-21"))
    );
    private final List<PublisherPOJO> invalidPublisherList = Arrays.asList(
            new PublisherPOJO("AST"),
            new PublisherPOJO(null),
            new PublisherPOJO("AKG000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
            new PublisherPOJO("       "),
            new PublisherPOJO("AST")
    );
    private final List<BookPOJO> invalidBookList = Arrays.asList(
            new BookPOJO("For Whom the Bell Tolls", "  ", "about war",
                    Collections.singleton("Ernest Hemingway"), LocalDate.of(1940, 9, 21),
                    "AMG", new BigDecimal("3.99"), Category.DRAMA),
            new BookPOJO("Foundation", "0001", "fantastic",
                    Collections.singleton("Isaac Asimov"), LocalDate.of(1985, 3, 23),
                    "AST", new BigDecimal("5.86"), null),
            new BookPOJO("Anna on the neck", "0002", "funny",
                    Collections.singleton("Anton Chekhov"), LocalDate.of(1962, 1, 1),
                    "AKG", new BigDecimal("1.76"), Category.COMEDY),
            new BookPOJO("Foo", "0003", "foo", Stream.of("Isaac Asimov", null).collect(Collectors.toSet()),
                    LocalDate.of(1999, 1, 1),
                    "ABS", new BigDecimal("7.99"), Category.HORROR),
            new BookPOJO("Anna on the neck", "0002", "funny",
                    Collections.singleton("Anton Chekhov"), LocalDate.of(1962, 1, 1),
                    "AKG", new BigDecimal("1.76"), Category.COMEDY),
            new BookPOJO("Anna on the neck", "0002", "funny",
                    Collections.singleton("Anton Chekhov"), LocalDate.of(1962, 1, 1),
                    "AKG", new BigDecimal("1.76"), Category.COMEDY)
    );
    private POJOValidator validator = new POJOValidatorImpl();

    @Test
    public void testValidateAndDistinctValidPublishers() {
        List<PublisherPOJO> publisherPOJOList = new ArrayList<>(validator.validateAndDistinct(validPublisherList));
        assertTrue(validPublisherList.size() == publisherPOJOList.size() &&
                validPublisherList.containsAll(publisherPOJOList));
    }

    @Test
    public void testValidateAndDistinctValidAuthors() {
        List<AuthorPOJO> authorPOJOList = new ArrayList<>(validator.validateAndDistinct(validAuthorList));
        assertTrue(validAuthorList.size() == authorPOJOList.size() &&
                validAuthorList.containsAll(authorPOJOList));
    }

    @Test
    public void testValidateAndDistinctValidBooks() {
        List<BookPOJO> bookPOJOList = new ArrayList<>(validator.validateAndDistinct(validBookList));
        assertTrue(validBookList.size() == bookPOJOList.size() &&
                validBookList.containsAll(bookPOJOList));
    }

    @Test
    public void testValidateAndDistinctInvalidPublishers() {
        assertEquals(validator.validateAndDistinct(invalidPublisherList),
                Collections.singletonList(invalidPublisherList.get(0)));
    }

    @Test
    public void testValidateAndDistinctInvalidAuthors() {
        assertEquals(validator.validateAndDistinct(invalidAuthorList),
                Collections.singletonList(invalidAuthorList.get(1)));
    }

    @Test
    public void testValidateAndDistinctInvalidBooks() {
        assertEquals(validator.validateAndDistinct(invalidBookList),
                Collections.singletonList(invalidBookList.get(2)));
    }
}
