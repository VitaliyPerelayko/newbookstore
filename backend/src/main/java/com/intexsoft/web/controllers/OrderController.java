package com.intexsoft.web.controllers;

import com.intexsoft.dao.model.Book;
import com.intexsoft.dao.model.Order;
import com.intexsoft.dao.model.OrderProducts;
import com.intexsoft.service.entityservice.BookService;
import com.intexsoft.service.entityservice.OrderProductsService;
import com.intexsoft.service.entityservice.OrderService;
import com.intexsoft.web.dto.mapping.OrderDTOMapper;
import com.intexsoft.web.dto.request.BookRequestForOrderDTO;
import com.intexsoft.web.dto.request.OrderRequestDTO;
import com.intexsoft.web.dto.response.OrderResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderDTOMapper orderDTOMapper;
    private final BookService bookService;
    private final OrderProductsService orderProductsService;

    public OrderController(OrderService orderService, OrderDTOMapper orderDTOMapper, BookService bookService,
                           OrderProductsService orderProductsService) {
        this.orderService = orderService;
        this.orderDTOMapper = orderDTOMapper;
        this.bookService = bookService;
        this.orderProductsService = orderProductsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(orderDTOMapper.mapOrderToOrderResponseDTO(orderService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> save(@Valid @RequestBody OrderRequestDTO orderRequestDTO) {
        Assert.isNull(orderRequestDTO.getId(), "Saved order's id must be null");
        return ResponseEntity.ok(orderDTOMapper.mapOrderToOrderResponseDTO(
                saveOrUpdateOrder(orderRequestDTO)
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> update(@PathVariable Long id, @Valid @RequestBody OrderRequestDTO orderRequestDTO) {
        Long orderId = orderRequestDTO.getId();
        Assert.notNull(orderId, "Saved order's id must be not null");
        Assert.state(orderId.equals(id), "Id in URL path and id in request body must be the same");
        return ResponseEntity.ok(orderDTOMapper.mapOrderToOrderResponseDTO(
                saveOrUpdateOrder(orderRequestDTO)
        ));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        Order order = orderService.findById(id);
        if (order.getClosed()) {
            orderService.delete(id);
        }
        if (order.getPaid()){
            // return money and:
            deleteOrder(order);
        } else {
            deleteOrder(order);
        }
    }

    private void deleteOrder(Order order){
        Stream<OrderProducts> orderProductsStream = order.getOrderProducts().stream();
        Map<Long, Short> mapNumberOfBook = orderProductsStream.
                collect(Collectors.toMap(o -> o.getBook().getId(),
                        o -> (short) (o.getBook().getNumber() + o.getNumber())));
        mapNumberOfBook.forEach(bookService::setNumberOfBook);
        orderService.delete(order.getId());
    }

    private Order saveOrUpdateOrder(OrderRequestDTO orderRequestDTO) {
        Order order = orderDTOMapper.mapOrderRequestDTOToOrder(orderRequestDTO);
        // set dateTime
        order.setDateTime(LocalDateTime.now());
        final Order savedOrder = orderService.save(order);

        // save OrderProducts
        List<Short> numbers = saveAllOrderProducts(orderRequestDTO, savedOrder);

        // change number of book in database
        List<BookRequestForOrderDTO> bookR = orderRequestDTO.getBooks();
        for (int i = 0; i< numbers.size(); i++){
            BookRequestForOrderDTO booksInOrder = bookR.get(i);
            int result = numbers.get(i) - booksInOrder.getNumber();
            bookService.setNumberOfBook(booksInOrder.getBookId(), (short) result);
        }

        return order;
    }

    private List<Short> saveAllOrderProducts(OrderRequestDTO orderRequestDTO, final Order order) {
        final List<Short> numbersOfBook = new ArrayList<>();
        orderRequestDTO.getBooks().forEach(bookR -> {
            OrderProducts orderProducts = new OrderProducts();
            Book book = bookService.findByIdLazy(bookR.getBookId());
            Assert.state(book.getNumber() >= bookR.getNumber(),
                    "Error. Number of books in order greater than number of books in the store");
            numbersOfBook.add(book.getNumber());
            orderProducts.setBook(book);
            orderProducts.setOrder(order);
            orderProducts.setNumber(bookR.getNumber());
            orderProductsService.saveAndUpdate(orderProducts);
        });
        return numbersOfBook;
    }
}
