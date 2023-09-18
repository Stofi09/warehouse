package com.warehouse.warehouse.service;

import com.warehouse.warehouse.model.MarketUser;
import com.warehouse.warehouse.model.dto.LoginDTO;
import com.warehouse.warehouse.model.dto.ResponseDTO;
import com.warehouse.warehouse.model.dto.UserDTO;
import com.warehouse.warehouse.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private EncryptionService   encryptionService;
    private JWTService jwtService;
    public UserService(UserRepository userRepository, EncryptionService encryptionService, JWTService jwtService) {
        this.userRepository = userRepository;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
    }

    public ResponseDTO registerUser(UserDTO userDTO){

        Optional<MarketUser> existingUserResponse = checkForExistingUser(userDTO);
        if(existingUserResponse.isPresent()) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST, "Email or Name already exists");
        }

        MarketUser user = new MarketUser();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(encryptionService.encryptPassword(userDTO.getPassword()));

        userRepository.save(user);

        return new ResponseDTO(HttpStatus.CREATED, "User registered successfully");
    }


    private Optional<MarketUser> checkForExistingUser(UserDTO userDTO) {
        // Check if name already exists in the database
        if(userRepository.findByNameIgnoreCase(userDTO.getName()).isPresent()){
            return userRepository.findByNameIgnoreCase(userDTO.getName());
        }
        // Check if email already exists in the database
        if(userRepository.findByEmail(userDTO.getEmail()).isPresent()){
            return userRepository.findByEmail(userDTO.getEmail());
        }
        return Optional.empty();
    }

    public String loginUser(LoginDTO loginDTO){
        Optional<MarketUser> existingUser = userRepository.findByNameIgnoreCase(loginDTO.getName());
        if(existingUser.isPresent()){
            if(encryptionService.verifyPassword(loginDTO.getPassword(),existingUser.get().getPassword())){
                return   jwtService.generateJWT(existingUser.get());
            }
        }
        return null;
    }

    public Optional<MarketUser> findByUserName(String name){
        return userRepository.findByNameIgnoreCase(name);
    }
}
