package com.warehouse.warehouse.service;

import com.warehouse.warehouse.model.MarketUser;
import com.warehouse.warehouse.model.UserOrder;

import java.util.List;

public interface IOrderService {
    List<UserOrder> getOrders(MarketUser user);
}
