package com.kepa.springlibraryapp.book;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookService bookService;


    @Test
    void findAll_shouldCallFindAllInRepository() {
        //given
        given(bookRepository.findAll()
                .stream()
                .map(BookMapper::toDto)
                .collect(Collectors.toList())).willReturn(new ArrayList<>());

        //when
        List<BookDto> books = bookService.findAll();

        //then
        then(bookRepository).should().findAll();
        assertThat(books).isNotNull();
    }

    @Test
    void findByIdTest_shouldPassParams() {
        //given
        Long id = 1L;
        Book bookEntity = new Book(
                "ISBN",
                "name",
                "author",
                BookCategory.HORROR,
                22.22,
                222,
                "description",
                50,
                "url"
        );
        given(bookRepository.findById(id)).willReturn(Optional.of(bookEntity));

        //when
        bookService.findById(id);

        //then
        then(bookRepository).should().findById(id);
    }

    @Test
    void findAllByNameOrAuthor_shouldPassParams() {
        //given
        String text = "test";
        given(bookRepository.findAllByNameOrAuthor(text)
                .stream()
                .map(BookMapper::toDto)
                .collect(Collectors.toList())).willReturn(new ArrayList<>());

        //when
        List<BookDto> books = bookService.findAllByNameOrAuthor(text);

        //then
        then(bookRepository).should().findAllByNameOrAuthor(text);
        assertThat(books).isNotNull();
    }
}
