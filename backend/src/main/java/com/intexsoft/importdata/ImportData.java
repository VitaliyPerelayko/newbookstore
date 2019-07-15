package com.intexsoft.importdata;

import com.intexsoft.importdata.pojo.AuthorPOJO;
import com.intexsoft.importdata.pojo.BookPOJO;
import com.intexsoft.importdata.pojo.PublisherPOJO;

import java.util.List;

public interface ImportData {

    List<AuthorPOJO> importAuthors();

    List<BookPOJO> importBooks();

    List<PublisherPOJO> importPublishers();
}
