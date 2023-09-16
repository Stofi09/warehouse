package com.warehouse.warehouse.service;

import com.warehouse.warehouse.model.MarketUser;
import com.warehouse.warehouse.model.dto.ResponseDTO;
import com.warehouse.warehouse.model.dto.UserDTO;
import com.warehouse.warehouse.repository.UserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseDTO registerUser(UserDTO userDTO){

        Optional<ResponseDTO> existingUserResponse = checkForExistingUser(userDTO);
        if(existingUserResponse.isPresent()) {
            return existingUserResponse.get();
        }

        MarketUser user = new MarketUser();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        userRepository.save(user);

        return new ResponseDTO(HttpStatus.CREATED, "User registered successfully");
    }


    private Optional<ResponseDTO> checkForExistingUser(UserDTO userDTO) {
        // Check if name already exists in the database
        if(userRepository.findByName(userDTO.getName()).isPresent()){
            return Optional.of(new ResponseDTO(HttpStatus.BAD_REQUEST, "Name already exists"));
        }
        // Check if email already exists in the database
        if(userRepository.findByEmail(userDTO.getEmail()).isPresent()){
            return Optional.of(new ResponseDTO(HttpStatus.BAD_REQUEST, "Email already exists"));
        }
        return Optional.empty();
    }

}
