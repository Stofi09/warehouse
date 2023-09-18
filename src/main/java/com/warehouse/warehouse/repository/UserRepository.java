package com.warehouse.warehouse.repository;

import com.warehouse.warehouse.model.MarketUser;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends ListCrudRepository<MarketUser, Long> {

    Optional<MarketUser> findByEmail(String email);
    Optional<MarketUser>  findByNameIgnoreCase(String name);
}
