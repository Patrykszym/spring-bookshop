package com.kepa.springlibraryapp.book;

import lombok.Value;

@Value
public class BookDto {
    Long id;
    String isbn;
    String name;
    String author;
    BookCategory bookCategory;
    Double price;
    int pages;
    String description;
    int stock;
    String imgUrl;
}
