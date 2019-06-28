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

    //TODO: make tests with asserts

    @Test
    public void testData() {
        Publisher publisher = new Publisher();
        when(publisherService.save(any(Publisher.class))).thenReturn(publisher);
        Author author = new Author();
        when(authorService.save(any(Author.class))).thenReturn(author);
        Book book = new Book();
        when(bookService.save(any(Book.class))).thenReturn(book);


        importData.saveAuthors().forEach(author1 -> {
            System.out.println(author1.getName());
            System.out.println(author1.getBio());
            System.out.println(author1.getBirthDate());
        });

        importData.saveBooks().forEach(book1 -> {
            System.out.println(book1.getName());
            System.out.println(book1.getCategory());
            System.out.println(book1.getDescription());
            System.out.println(book1.getPublisher());
            System.out.println(book1.getPrice());
            System.out.println(book1.getPublishDate());
            book1.getAuthors().forEach(System.out::println);
        });

        importData.savePublishers().forEach(publisher1 ->
                System.out.println(publisher1.getName()));
    }
}
