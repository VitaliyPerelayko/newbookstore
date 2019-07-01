package com.intexsoft.service.importdata;

import com.intexsoft.dao.model.Author;
import com.intexsoft.dao.model.Book;
import com.intexsoft.dao.model.Publisher;

import java.util.List;

public interface ImportData {

    List<Author> importAuthors();

    List<Book> importBooks();

    List<Publisher> importPublishers();
}
