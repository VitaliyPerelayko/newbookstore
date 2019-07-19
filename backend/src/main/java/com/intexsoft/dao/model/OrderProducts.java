package com.intexsoft.dao.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "order_has_books")
public class OrderProducts {

    @Id
    @EmbeddedId
    private OrderProductId id = new OrderProductId();

    @MapsId("bookId")
    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    @NotNull
    private Book book;


    @MapsId("orderId")
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @NotNull
    private Order order;

    @NotNull(message = "Field number in OrderProducts must be not null")
    @Positive(message = "Field number in OrderProducts must be positive")
    private Short number;

    public OrderProducts(Book book, Order order, Short number) {
        this.book = book;
        this.order = order;
        this.number = number;
    }

    public OrderProducts() {
    }

    public OrderProductId getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Short getNumber() {
        return number;
    }

    public void setNumber(Short number) {
        this.number = number;
    }
}
