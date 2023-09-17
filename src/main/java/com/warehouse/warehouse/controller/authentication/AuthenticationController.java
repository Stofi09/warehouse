package com.warehouse.warehouse.controller.authentication;

import com.warehouse.warehouse.model.MarketUser;
import com.warehouse.warehouse.model.dto.LoginDTO;
import com.warehouse.warehouse.model.dto.ResponseDTO;
import com.warehouse.warehouse.model.dto.UserDTO;
import com.warehouse.warehouse.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/v1")
public class AuthenticationController {

    private UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserDTO userDTO) {
        ResponseDTO response = userService.registerUser(userDTO);
        return new ResponseEntity<>(response.getMessage(), response.getStatus());
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@Valid @RequestBody LoginDTO loginDTO){
        String jwt = userService.loginUser(loginDTO);
        if(jwt == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else{
            ResponseDTO response = new ResponseDTO(HttpStatus.CREATED, jwt);
            return ResponseEntity.ok().body(response);
        }
    }
    @GetMapping("/me")
    public MarketUser getLoggedInUserProfile(@AuthenticationPrincipal MarketUser user) {
        return user;
    }
}
