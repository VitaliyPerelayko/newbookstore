package com.intexsoft.web.dto.mapping;

import com.intexsoft.dao.model.Order;
import com.intexsoft.dao.model.OrderProducts;
import com.intexsoft.dao.model.User;
import com.intexsoft.service.entityservice.BookService;
import com.intexsoft.service.entityservice.UserService;
import com.intexsoft.web.dto.request.BookRequestForOrderDTO;
import com.intexsoft.web.dto.request.OrderRequestDTO;
import com.intexsoft.web.dto.response.BookResponseForOrderDTO;
import com.intexsoft.web.dto.response.OrderResponseDTO;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Service
public class OrderDTOMapper {

    private final BookDTOMapper bookDTOMapper;
    private final UserService userService;
    private final BookService bookService;

    public OrderDTOMapper(BookDTOMapper bookDTOMapper, UserService userService, BookService bookService) {
        this.bookDTOMapper = bookDTOMapper;
        this.userService = userService;
        this.bookService = bookService;
    }

    public OrderResponseDTO mapOrderToOrderResponseDTO(@Valid Order order) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setAddress(order.getDeliveryAddress());
        orderResponseDTO.setBooks(order.getOrderProducts().stream()
                .map(this::mapOrderProductsToBookResponseForOrderDTO).collect(Collectors.toList()));
        orderResponseDTO.setComment(order.getComment());
        orderResponseDTO.setDateTime(order.getDateTime());
        orderResponseDTO.setId(order.getId());
        orderResponseDTO.setPaid(order.getPaid());
        orderResponseDTO.setTotalPrice(order.getTotalPrice());
        User user = order.getUser();
        orderResponseDTO.setUserId(user.getId());
        orderResponseDTO.setUserName(user.getName());
        orderResponseDTO.setUserSurname(user.getSurname());
        return orderResponseDTO;
    }

    public Order mapOrderRequestDTOToOrder(OrderRequestDTO orderRequestDTO) {
        Order order = new Order();
        order.setId(orderRequestDTO.getId());
        order.setComment(orderRequestDTO.getComment());
        order.setDeliveryAddress(orderRequestDTO.getDeliveryAddress());
        order.setUser(userService.getOne(orderRequestDTO.getUserId()));
        order.setTotalPrice(orderRequestDTO.getTotalPrice());
        order.setOrderProducts(orderRequestDTO.getBooks().stream()
                .map(this::mapBookRequestForOrderDTOToOrderProduct).collect(Collectors.toSet()));
        return order;
    }

    private OrderProducts mapBookRequestForOrderDTOToOrderProduct(BookRequestForOrderDTO bookR) {
        OrderProducts orderProducts = new OrderProducts();
        orderProducts.setBook(bookService.findByIdLazy(bookR.getBookId()));
        orderProducts.setNumber(bookR.getNumber());
        return orderProducts;
    }

    private BookResponseForOrderDTO mapOrderProductsToBookResponseForOrderDTO(@Valid OrderProducts products) {
        BookResponseForOrderDTO bookResponseForOrderDTO =
                bookDTOMapper.mapBookToBookResponseForOrderDTO(products.getBook());
        bookResponseForOrderDTO.setNumber(products.getNumber());
        return bookResponseForOrderDTO;
    }
}
