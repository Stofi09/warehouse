package com.warehouse.warehouse.repository;

import com.warehouse.warehouse.model.UserOrder;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOrderRepository extends ListCrudRepository<UserOrder,Long> {

}
