package com.kepa.springlibraryapp.order;

import com.kepa.springlibraryapp.book.Book;
import com.kepa.springlibraryapp.user.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class OrderDto {
    private User user;
    private List<Book> books = new ArrayList<>();
    private OrderDetails orderDetails;
    private OrderStatus status;
}
