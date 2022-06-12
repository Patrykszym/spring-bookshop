package com.kepa.springlibraryapp.order;

import com.kepa.springlibraryapp.book.Book;
import com.kepa.springlibraryapp.book.BookRepository;
import com.kepa.springlibraryapp.user.User;
import com.kepa.springlibraryapp.user.UserRepository;
import com.kepa.springlibraryapp.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;



@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    ClientOrder clientOrder;

    @Mock
    BookRepository bookRepository;

    @Mock
    OrderRepository orderRepository;

    @Mock
    UserRepository userRepository;


    @Mock
    UserService userService;

    @Mock
    OrderDetailsRepository orderDetailsRepository;

    @InjectMocks
    OrderService orderService;


    @Test
    void addBookToOrder_shouldPassParams() {
        //given
        Long id = 1L;
        Book bookEntity = new Book(
                null,
                "test",
                null,
                null,
                null,
                222,
                null,
                100,
                null
        );
        given(bookRepository.findById(id)).willReturn(Optional.of(bookEntity));

        //when
        Optional<Book> book = orderService.addBookToOrder(id);

        //then
        then(bookRepository).should().findById(id);
        then(clientOrder).should().add(book.get());
        assertThat(book).isNotEmpty();
        assertThat(book.get().getName()).isEqualTo("test");
    }

    @Test
    void deleteBookFromOrder_shouldCallGetOrder() {
        //given
        Long id = 1L;
        List<Book> books = new ArrayList<>();
        Book book1 = new Book();
        Book book2 = new Book();
        books.add(book1);
        books.add(book2);
        OrderDto order = new OrderDto();
        ReflectionTestUtils.setField(order, "books", books);
        given(clientOrder.getOrder()).willReturn(order);

        //when
        orderService.deleteBookFromOrder(id);

        //then
        then(clientOrder).should().getOrder();
    }


    @Test
    void proceedOrder_shouldPassParams() {
        //given
        Authentication authentication = auth();
        OrderDetailsDto orderDetails = new OrderDetailsDto("test", "777124214");
        OrderDto order = new OrderDto();
        given(userRepository.findByEmailOpt(authentication.getName())).willReturn(Optional.of(new User()));
        given(clientOrder.getOrder()).willReturn(order);

        //when
        orderService.proceedOrder(orderDetails, authentication);

        //then
        then(userRepository).should().findByEmailOpt(authentication.getName());
        then(userRepository).should().findByEmailOpt(authentication.getName() + "@github.com");
        then(orderDetailsRepository).should().save(any());
        then(clientOrder).should().getOrder();
        then(orderRepository).should().save(any());
        then(clientOrder).should().clear();
    }

    @Test
    void sumOrderCost_shouldCallGetOrderAndSumOrderPrice() {
        //given
        List<Book> books = new ArrayList<>();
        Book book1 = new Book();
        ReflectionTestUtils.setField(book1, "price", 8.00);
        Book book2 = new Book();
        ReflectionTestUtils.setField(book2, "price", 1.24);
        books.add(book1);
        books.add(book2);
        OrderDto order = new OrderDto();
        ReflectionTestUtils.setField(order, "books", books);
        given(clientOrder.getOrder()).willReturn(order);

        //when
        double sum = orderService.sumOrderCost();

        //then
        then(clientOrder).should()
                .getOrder();
        assertThat(sum).isEqualTo(9.24);

    }


    Authentication auth(){
        return new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean b) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return "test";
            }
        };
    }
}
