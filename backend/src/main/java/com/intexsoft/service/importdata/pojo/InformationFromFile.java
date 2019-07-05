package com.intexsoft.service.importdata.pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "data")
public class InformationFromFile {

    private List<PublisherPOJO> publishersList;

    private List<AuthorPOJO> authorsList;

    private List<BookPOJO> booksList;

    public List<PublisherPOJO> getPublishersList() {
        return publishersList;
    }

    @XmlElementWrapper(name = "publishers")
    @XmlElement(name = "publisher")
    public void setPublishersList(List<PublisherPOJO> publishersList) {
        this.publishersList = publishersList;
    }

    public List<AuthorPOJO> getAuthorsList() {
        return authorsList;
    }

    @XmlElementWrapper(name = "authors")
    @XmlElement(name = "author")
    public void setAuthorsList(List<AuthorPOJO> authorsList) {
        this.authorsList = authorsList;
    }

    public List<BookPOJO> getBooksList() {
        return booksList;
    }

    @XmlElementWrapper(name = "books")
    @XmlElement(name = "book")
    public void setBooksList(List<BookPOJO> booksList) {
        this.booksList = booksList;
    }
}
