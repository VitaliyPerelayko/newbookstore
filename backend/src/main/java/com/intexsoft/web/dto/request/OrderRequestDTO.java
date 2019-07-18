package com.intexsoft.web.dto.request;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public class OrderRequestDTO {
    private Long id;

    @Size(max = 150, message = "Number of characters in order's comment must be less than 150")
    private String comment;

    @NotBlank(message = "Address of delivery in orders must be not null")
    @Size(max = 100, message = "Number of characters in order's address must be less than 100")
    private String deliveryAddress;

    @NotNull(message = "Order's field user must be not null. Order must belong to some user")
    private Long userId;

    @NotEmpty(message = "Order must have at least one book. List of bookId in Order request must be not empty")
    private List<
            @NotNull(message = "Each book in order must de not null")
            BookRequestForOrderDTO> books;

    @NotNull(message = "Order's totalPrice must be not null")
    @Positive(message = "Orders's totalPrice can not be negative")
    private BigDecimal totalPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<BookRequestForOrderDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookRequestForOrderDTO> books) {
        this.books = books;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
