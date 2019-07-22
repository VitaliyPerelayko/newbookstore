package com.intexsoft.service.entityservice.impl;

import com.intexsoft.dao.model.Order;
import com.intexsoft.dao.model.OrderProducts;
import com.intexsoft.dao.repository.OrderRepository;
import com.intexsoft.service.entityservice.BookService;
import com.intexsoft.service.entityservice.OrderProductsService;
import com.intexsoft.service.entityservice.OrderService;
import com.intexsoft.web.dto.request.BookRequestForOrderDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BookService bookService;
    private final OrderProductsService orderProductsService;

    public OrderServiceImpl(OrderRepository orderRepository, BookService bookService,
                            OrderProductsService orderProductsService) {
        this.orderRepository = orderRepository;
        this.bookService = bookService;
        this.orderProductsService = orderProductsService;
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Order with the given id isn't exist"));
    }

    @Override
    public Order save(@Valid Order order) {
        Assert.state(order.getId() == null, "Order which is saved must hasn't id");
        final Order savedOrder = orderRepository.saveAndFlush(order);
        saveAllOrderProducts(order.getOrderProducts(), savedOrder).
                forEach(bookService::setNumberOfBookSubtract);
        return findById(savedOrder.getId());
    }

    @Override
    public Order update(@Valid Order order) {
        Assert.state(order.getId() != null, "Order which is updated must has id");
        // change books in order
        Order updatingOrder = findById(order.getId());
        final Set<OrderProducts> products = order.getOrderProducts();
        order.setOrderProducts(null);
        final Order updatedOrder = orderRepository.saveAndFlush(order);
        updatedOrder.setOrderProducts(products);
        changeBooksInOrder(updatingOrder, updatedOrder);
        return findById(updatedOrder.getId());
    }

    @Override
    public void delete(Long id) {
        Order order = findById(id);
        if (order.getClosed()) {
            orderRepository.deleteById(id);
        }
        if (order.getPaid()) {
            // return money and:
            returnBooksToDatabase(order);
            orderRepository.deleteById(id);

        }
        returnBooksToDatabase(order);
        orderRepository.deleteById(id);
    }

    private void returnBooksToDatabase(Order order) {
        Map<Long, Short> mapNumberOfBook = order.getOrderProducts().stream().
                collect(Collectors.toMap(o -> o.getBook().getId(),
                        OrderProducts::getNumber));
        mapNumberOfBook.forEach(bookService::setNumberOfBookPlus);
    }

    private Map<Long, Short> saveAllOrderProducts(final Set<OrderProducts> orderProducts, final Order order) {
        final Map<Long, Short> numbersOfBook = new HashMap<>();
        orderProducts.forEach(products -> {
            Short booksAvailable = products.getBook().getNumber();
            Short booksInOrder = products.getNumber();
            Assert.state(booksAvailable >= booksInOrder,
                    "Error. Number of books in order greater than number of books in the store");
            numbersOfBook.put(products.getBook().getId(), booksInOrder);
            products.setOrder(order);
            orderProductsService.saveAndUpdate(products);
        });
        return numbersOfBook;
    }

    /**
     * НЕ ЛЕГЧЕ ЛИ ВСЁ УДАЛИТЬ И СОХРАНИТЬ ПО НОВОМУ???
     *
     */
    private void changeBooksInOrder(Order updatingOrder, Order order) {
        final Map<Long, Short> orderProducts = order.getOrderProducts().stream()
                .collect(Collectors.toMap(o -> o.getBook().getId(), OrderProducts::getNumber));
        updatingOrder.getOrderProducts().forEach(product -> {
            Long bookId = product.getBook().getId();
            Short number = orderProducts.get(bookId);
            // new order hasn't this product or numbers of book don't match
            if ((number == null) || (!number.equals(product.getNumber()))){
                // return books
                bookService.setNumberOfBookPlus(bookId, product.getNumber());
                // delete orderProduct
                orderProductsService.delete(product.getId());
            } else {
                orderProducts.remove(bookId , number);
            }
        });
        final Set<OrderProducts> orderProductsSet = order.getOrderProducts().stream().filter(product ->
                orderProducts.keySet().contains(product.getBook().getId())).collect(Collectors.toSet());
        saveAllOrderProducts(orderProductsSet, order).forEach(bookService::setNumberOfBookSubtract);
    }
}
