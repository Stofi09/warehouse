package com.warehouse.warehouse.controller.authentication;

import com.warehouse.warehouse.model.dto.LoginBodyDTO;
import com.warehouse.warehouse.model.dto.LoginResponseDTO;
import com.warehouse.warehouse.model.dto.ResponseDTO;
import com.warehouse.warehouse.model.dto.UserDTO;
import com.warehouse.warehouse.service.UserService;
import com.warehouse.warehouse.utility.exception.EmailFailureException;
import com.warehouse.warehouse.utility.exception.UserNotVerifiedException;
import jakarta.validation.Valid;
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
    public ResponseEntity<String> register(@Valid @RequestBody UserDTO userDTO) throws EmailFailureException {
        ResponseDTO response = userService.registerUser(userDTO);
        return new ResponseEntity<>(response.getMessage(), response.getStatus());
    }

/*    @PostMapping("/login")
    public ResponseEntity loginUser(@Valid @RequestBody LoginDTO loginDTO) throws UserNotVerifiedException, EmailFailureException {
        String jwt = userService.loginUser(loginDTO);
        if(jwt == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else{
            ResponseDTO response = new ResponseDTO(HttpStatus.CREATED, jwt);
            return ResponseEntity.ok().body(response);
        }
    }


 */
@PostMapping("/login")
public ResponseEntity<LoginResponseDTO> loginUser(@Valid @RequestBody LoginBodyDTO loginBody)throws UserNotVerifiedException, EmailFailureException {
    String jwt = null;
    try {
        jwt = userService.loginUser(loginBody);
    } catch (UserNotVerifiedException ex) {
        LoginResponseDTO response = new LoginResponseDTO();
        response.setSuccess(false);
        String reason = "USER_NOT_VERIFIED";
        if (ex.isNewEmailSent()) {
            reason += "_EMAIL_RESENT";
        }
        response.setFailureReason(reason);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    } catch (EmailFailureException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    if (jwt == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    } else {
        LoginResponseDTO response = new LoginResponseDTO();
        response.setJwt(jwt);
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }
}
    @PostMapping("/verify")
    public ResponseEntity verifyEmail(@RequestParam String token) {
        if (userService.verifyUser(token)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
