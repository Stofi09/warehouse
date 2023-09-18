package com.warehouse.warehouse.repository;

import com.warehouse.warehouse.model.MarketUser;
import com.warehouse.warehouse.model.UserOrder;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserOrderRepository extends ListCrudRepository<UserOrder,Long> {
    List<UserOrder> findByUser(MarketUser user);

}
