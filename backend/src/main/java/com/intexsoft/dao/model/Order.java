package com.intexsoft.dao.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "books_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 150, message = "Number of characters in order's comment must be less than 150")
    private String comment;

    @Column(name = "deliver_address", nullable = false)
    @NotBlank(message = "Address of delivery in orders must be not null")
    @Size(max = 100, message = "Number of characters in order's address must be less than 100")
    private String deliveryAddress;

    @Column(name = "date_time", nullable = false)
    @NotNull(message = "Order's dateTime must be not null")
    @Past(message = "Date of order can not be in future")
    private LocalDateTime dateTime;

    @Column(name = "total_price", nullable = false)
    @NotNull(message = "Order's totalPrice must be not null")
    @Positive(message = "Orders's totalPrice can not be negative")
    private BigDecimal totalPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull(message = "Order's field user must be not null. Order must belong to some user")
    private User user;

    @Column(name = "is_paid", nullable = false)
    @NotNull(message = "Order's field isPaid must be not null")
    private Boolean isPaid;

    @Column(name = "is_closed", nullable = false)
    @NotNull(message = "Order's field isClosed must be not null")
    private Boolean isClosed;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<OrderProducts> orderProducts;

    public Order(@Size(max = 150, message = "Number of characters in order's comment must be less than 150")
                         String comment,
                 @NotBlank(message = "Address of delivery in orders must be not null")
                 @Size(max = 100, message = "Number of characters in order's address must be less than 100")
                         String deliveryAddress,
                 @NotNull(message = "Order's dateTime must be not null")
                 @Past(message = "Date of order can not be in future")
                         LocalDateTime dateTime,
                 @NotNull(message = "Order's totalPrice must be not null")
                 @Positive(message = "Orders's totalPrice can not be negative")
                         BigDecimal totalPrice,
                 @NotNull(message = "Order's field user must be not null. Order must belong to some user")
                         User user
                 ) {
        this.comment = comment;
        this.deliveryAddress = deliveryAddress;
        this.dateTime = dateTime;
        this.totalPrice = totalPrice;
        this.user = user;
        this.isPaid = false;
        this.isClosed = false;
    }

    public Order() {
        this.isPaid = false;
        this.isClosed = false;
    }

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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }

    public Set<OrderProducts> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(Set<OrderProducts> orderProducts) {
        this.orderProducts = orderProducts;
    }
}
