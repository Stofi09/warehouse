package com.warehouse.warehouse.controller.authentication;

import com.warehouse.warehouse.model.dto.ResponseDTO;
import com.warehouse.warehouse.model.dto.UserDTO;
import com.warehouse.warehouse.service.UserService;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/v1")
public class AuthenticationController {

    private UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        ResponseDTO response = userService.registerUser(userDTO);
        return new ResponseEntity<>(response.getMessage(), response.getStatus());
    }
}
