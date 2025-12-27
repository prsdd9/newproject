package com.psd.orderserive.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psd.orderserive.model.Order;

public interface OrderRepository  extends JpaRepository<Order, Long> {

}
