package com.kepa.springlibraryapp.order;

import org.springframework.data.jpa.repository.JpaRepository;

interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
}
