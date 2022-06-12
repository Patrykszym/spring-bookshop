package com.kepa.springlibraryapp.order;

import com.kepa.springlibraryapp.book.Book;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
class ClientOrder {

    private OrderDto order;

    public ClientOrder() {
        clear();
    }

    OrderDto getOrder() {
        return order;
    }

    void add(Book book) {
        order.getBooks().add(book);
    }

    void clear() {
        order = new OrderDto();
        order.setStatus(OrderStatus.NEW);
    }
}
