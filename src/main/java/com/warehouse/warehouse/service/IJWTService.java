package com.warehouse.warehouse.service;


import com.warehouse.warehouse.model.MarketUser;

public interface IJWTService {

    public String generateJWT(MarketUser user);
    String getUserName(String token);
    String generateVerificationJWT(MarketUser user);
}
