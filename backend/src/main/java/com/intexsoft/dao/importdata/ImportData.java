package com.intexsoft.dao.importdata;

import com.intexsoft.dao.model.Author;
import com.intexsoft.dao.model.Book;
import com.intexsoft.dao.model.Publisher;

import java.util.List;

public interface ImportData {
    void saveData();

    List<Author> saveAuthors();

    List<Book> saveBooks();

    List<Publisher> savePublishers();
}
