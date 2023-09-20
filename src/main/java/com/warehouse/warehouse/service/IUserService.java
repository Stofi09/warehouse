package com.warehouse.warehouse.service;

import com.warehouse.warehouse.model.MarketUser;
import com.warehouse.warehouse.model.dto.LoginBodyDTO;
import com.warehouse.warehouse.model.dto.LoginResponseDTO;
import com.warehouse.warehouse.model.dto.ResponseDTO;
import com.warehouse.warehouse.model.dto.UserDTO;
import com.warehouse.warehouse.utility.exception.EmailFailureException;
import com.warehouse.warehouse.utility.exception.UserNotVerifiedException;

import java.util.Optional;

public interface IUserService {
    ResponseDTO registerUser(UserDTO userDTO) throws EmailFailureException;
    String loginUser(LoginBodyDTO loginDTO) throws UserNotVerifiedException, EmailFailureException;
    Optional<MarketUser> findByUserName(String name);

    boolean verifyUser(String token);
}
