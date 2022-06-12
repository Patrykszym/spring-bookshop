package com.kepa.springlibraryapp.order;

import com.kepa.springlibraryapp.book.Book;
import com.kepa.springlibraryapp.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client_order")
@Getter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToMany
    @JoinTable(name = "order_book",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id")
    )
    private List<Book> books = new ArrayList<>();
    @OneToOne
    private OrderDetails orderDetails;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    Order(final User user, final List<Book> books, final OrderDetails orderDetails, final OrderStatus status) {
        this.user = user;
        this.books = books;
        this.orderDetails = orderDetails;
        this.status = status;
    }

    void nextStatus() {
        this.status = OrderStatus.nextStatus(this.status);
    }
}
