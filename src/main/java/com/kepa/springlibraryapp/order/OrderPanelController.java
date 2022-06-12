package com.kepa.springlibraryapp.order;

import com.kepa.springlibraryapp.book.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
class OrderPanelController {
    private final OrderPanelService orderPanelService;

    @GetMapping("/panel/orders")
    String getOrders(@RequestParam(required = false) OrderStatus status, Model model) {
        model.addAttribute("orders", orderPanelService.getOrders(status));
        return "panel/orders";
    }

    @GetMapping("/panel/order/{id}")
    String singleOrder(@PathVariable Long id, Model model) {
        Optional<Order> order = orderPanelService.findById(id);
        return order.map(o -> singleOrderPanel(o, model))
                .orElse("redirect:/");
    }

    @PostMapping("/panel/order/{id}")
    String changeOrderStatus(@PathVariable Long id, Model model) {
        Optional<Order> order = orderPanelService.changeOrderStatusToNext(id);
        return order.map(o -> singleOrderPanel(o, model))
                .orElse("redirect:/");
    }

    private String singleOrderPanel(Order order, Model model) {
        model.addAttribute("order", order);
        model.addAttribute("sum", order
                .getBooks()
                .stream()
                .mapToDouble(Book::getPrice)
                .sum());
        return "panel/order";
    }

}
