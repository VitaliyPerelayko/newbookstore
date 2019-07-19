package com.intexsoft.web.dto.request;

import javax.validation.constraints.NotNull;

public class BookRequestForOrderDTO {

    @NotNull(message = "bookId in order must be not null")
    private Long bookId;

    @NotNull(message = "number of books in order must be not null")
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
