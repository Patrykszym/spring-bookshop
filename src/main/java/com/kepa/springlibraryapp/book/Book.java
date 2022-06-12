package com.kepa.springlibraryapp.book;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String isbn;
    private String name;
    private String author;
    @Enumerated(EnumType.STRING)
    private BookCategory bookCategory;
    private Double price;
    private int pages;
    @Column(length = 1024)
    private String description;
    private int stock;
    private String imgUrl;

    public Book(
            final String isbn,
            final String name,
            final String author,
            final BookCategory bookCategory,
            final Double price, final int pages,
            final String description,
            final int stock,
            final String imgUrl
    ) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.bookCategory = bookCategory;
        this.price = price;
        this.pages = pages;
        this.description = description;
        this.stock = stock;
        this.imgUrl = imgUrl;
    }

    public void lowerStockByOne() {
        stock--;
    }
}
