package com.kepa.springlibraryapp.order;

import com.kepa.springlibraryapp.book.Book;
import com.kepa.springlibraryapp.book.BookRepository;
import com.kepa.springlibraryapp.user.User;
import com.kepa.springlibraryapp.user.UserRepository;
import com.kepa.springlibraryapp.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class OrderService {
    private final ClientOrder clientOrder;
    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final OrderDetailsRepository orderDetailsRepository;


    public Optional<Book> addBookToOrder(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        book.ifPresent(clientOrder::add);

        return book;
    }

    public void deleteBookFromOrder(Long bookIndex) {
        int index = bookIndex.intValue();
        OrderDto order = clientOrder.getOrder();
        order.getBooks().remove(index);
    }

    public void proceedOrder(OrderDetailsDto orderDetailsDto, Authentication authentication) {

        String email = null;
        if (authentication != null)
            email = authentication.getName();

        Optional<User> user = userRepository.findByEmailOpt(email);
        Optional<User> userGithub = userRepository.findByEmailOpt(email + "@github.com");

        OrderDetails orderDetails = new OrderDetails(
                orderDetailsDto.getAddress(),
                orderDetailsDto.getTelephone()
        );
        orderDetailsRepository.save(orderDetails);

        OrderDto order = clientOrder.getOrder();
        order.setOrderDetails(orderDetails);

        user.ifPresent(order::setUser);
        userGithub.ifPresent(order::setUser);

        if (user.isEmpty() && userGithub.isEmpty())
            order.setUser(OAuth2UserToUser(authentication, email));

        updateStock(order.getBooks());

        orderRepository.save(
                new Order(
                        order.getUser(),
                        order.getBooks(),
                        order.getOrderDetails(),
                        order.getStatus()
                )
        );
        clientOrder.clear();
    }

    public double sumOrderCost(){
        OrderDto order = clientOrder.getOrder();
        return order
                .getBooks().stream()
                .mapToDouble(Book::getPrice)
                .sum();
    }

    private void updateStock(List<Book> books) {
        books.forEach(book -> {
            book.lowerStockByOne();
            bookRepository.save(book);
        });
    }

    private User OAuth2UserToUser(Authentication authentication, String email) {
        OAuth2AuthenticationToken oAuth2AuthenticationToken = ((OAuth2AuthenticationToken) authentication);
        OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();
        Map<String, Object> map = oAuth2User.getAttributes();
        String name = (String) map.get("login");

        User newUser = new User();
        newUser.setEmail(email + "@github.com");
        newUser.setFirstname(name);
        newUser.setLastname(name);
        //TODO generate random password
        newUser.setPassword("default");
        newUser.setEnabled(true);

        userService.addWithDefaultRole(newUser);

        return newUser;
    }



}
