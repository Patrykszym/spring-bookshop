package com.kepa.springlibraryapp.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    BookService bookService;

    @Mock
    Model model;

    @InjectMocks
    BookController controller;

    List<BookDto> books = new ArrayList<>();
    BookDto bookDto;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        bookDto = new BookDto(
                1L,
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
        books.add(bookDto);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    void book_shouldReturnAViewContainingBook() {
        //given
        given(bookService.findById(1L)).willReturn(bookDto);

        //when
        String view = controller.book(1L, model);

        //then
        then(bookService).should().findById(1L);
        then(model).should().addAttribute(anyString(), any());
        assertThat("bookView").isEqualToIgnoringCase(view);
    }

    @Test
    void book_shouldReturn200AndAViewContainingBook() throws Exception {
        given(bookService.findById(1L)).willReturn(bookDto);

        mockMvc.perform((get("/book")).param("bookId", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("book"))
                .andExpect(view().name("bookView"));
    }

    @Test
    void findAll_shouldReturnAViewContainingBooks() {
        //given
        given(bookService.findAll()).willReturn(books);

        //when
        String view = controller.findAll(model, null);

        //then
        then(bookService).should().findAll();
        then(model).should().addAttribute(anyString(), any());
        assertThat("index").isEqualToIgnoringCase(view);
    }

    @Test
    void findAll_shouldReturn200AndAViewContainingBooks() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("books"))
                .andExpect(view().name("index"));
    }

    @Test
    void findAll_shouldReturn200AndAViewContainingBooks_whenPassedTextParam() throws Exception {
        mockMvc.perform(get("/").param("text", "test"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("books"))
                .andExpect(view().name("index"));
    }
}
