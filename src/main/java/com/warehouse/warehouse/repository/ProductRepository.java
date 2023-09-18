package com.warehouse.warehouse.repository;

import com.warehouse.warehouse.model.Product;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ListCrudRepository<Product,Long> {
}
