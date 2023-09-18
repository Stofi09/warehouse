package com.warehouse.warehouse.service;

import com.warehouse.warehouse.model.MarketUser;
import com.warehouse.warehouse.model.UserOrder;
import com.warehouse.warehouse.repository.UserOrderRepository;

import java.util.List;

public class OrderService implements IOrderService{
    private UserOrderRepository orderRepository;

    public OrderService(UserOrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    public List<UserOrder> getOrders(MarketUser user) {
        return this.orderRepository.findByUser(user);
    }
}
