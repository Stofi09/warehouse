package com.warehouse.warehouse.controller.order;

import com.warehouse.warehouse.model.MarketUser;
import com.warehouse.warehouse.model.UserOrder;
import com.warehouse.warehouse.service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order/v1")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }
    @GetMapping("/all")
    public List<UserOrder> getAll(@AuthenticationPrincipal MarketUser user){
        return this.orderService.getOrders(user);
    }
}
