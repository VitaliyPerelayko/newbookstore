package com.intexsoft.web.dto.request;

public class BookRequestForOrderDTO {

    private Long bookId;

    private Short number;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Short getNumber() {
        return number;
    }

    public void setNumber(Short number) {
        this.number = number;
    }
}
