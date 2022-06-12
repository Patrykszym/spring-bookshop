package com.kepa.springlibraryapp.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class OrderPanelService {
    private final OrderRepository orderRepository;

    List<Order> getOrders(OrderStatus status) {
        List<Order> orders;
        if (status == null)
            orders = orderRepository.findAll();
        else
            orders = orderRepository.findAllByStatus(status);
        return orders;
    }

    Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    Optional<Order> changeOrderStatusToNext(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        order.ifPresent(o -> {
            o.nextStatus();
            orderRepository.save(o);
        });
        return order;
    }
}
