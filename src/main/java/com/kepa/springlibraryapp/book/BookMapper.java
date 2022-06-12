package com.kepa.springlibraryapp.book;

class BookMapper {

    static BookDto toDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getIsbn(),
                book.getName(),
                book.getAuthor(),
                book.getBookCategory(),
                book.getPrice(),
                book.getPages(),
                book.getDescription(),
                book.getStock(),
                book.getImgUrl()
        );
    }
}
