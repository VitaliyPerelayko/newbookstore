package com.intexsoft.service.importdata;

import com.intexsoft.service.importdata.pojo.AuthorPOJO;
import com.intexsoft.service.importdata.pojo.BookPOJO;
import com.intexsoft.service.importdata.pojo.PublisherPOJO;

import java.util.List;

public interface ImportData {

    List<AuthorPOJO> importAuthors();

    List<BookPOJO> importBooks();

    List<PublisherPOJO> importPublishers();
}
