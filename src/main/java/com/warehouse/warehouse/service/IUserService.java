package com.warehouse.warehouse.service;

import com.warehouse.warehouse.model.MarketUser;
import com.warehouse.warehouse.model.dto.LoginDTO;
import com.warehouse.warehouse.model.dto.ResponseDTO;
import com.warehouse.warehouse.model.dto.UserDTO;

import java.util.Optional;

public interface IUserService {
    ResponseDTO registerUser(UserDTO userDTO);
    String loginUser(LoginDTO loginDTO);
    Optional<MarketUser> findByUserName(String name);
}
