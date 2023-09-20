package com.warehouse.warehouse.repository;

import com.warehouse.warehouse.model.MarketUser;
import com.warehouse.warehouse.model.VerificationToken;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends ListCrudRepository<VerificationToken,Long> {
    Optional<VerificationToken> findByToken(String token);

    void deleteByUser(MarketUser user);
}
