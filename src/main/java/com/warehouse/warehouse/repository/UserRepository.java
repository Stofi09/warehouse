package com.warehouse.warehouse.repository;

import com.warehouse.warehouse.model.MarketUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<MarketUser, Long> {
    Optional<MarketUser> findByName(String name);
    Optional<MarketUser> findByEmail(String email);
}
