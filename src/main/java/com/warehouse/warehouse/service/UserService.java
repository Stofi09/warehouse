package com.warehouse.warehouse.service;

import com.warehouse.warehouse.model.MarketUser;
import com.warehouse.warehouse.model.VerificationToken;
import com.warehouse.warehouse.model.dto.LoginBodyDTO;
import com.warehouse.warehouse.model.dto.LoginResponseDTO;
import com.warehouse.warehouse.model.dto.ResponseDTO;
import com.warehouse.warehouse.model.dto.UserDTO;
import com.warehouse.warehouse.repository.UserRepository;
import com.warehouse.warehouse.repository.VerificationTokenRepository;
import com.warehouse.warehouse.utility.exception.EmailFailureException;
import com.warehouse.warehouse.utility.exception.UserNotVerifiedException;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private UserRepository userRepository;
    private EncryptionService   encryptionService;
    private JWTService jwtService;
    private EmailService emailService;
    private VerificationTokenRepository verificationTokenRepository;

    public UserService(UserRepository userRepository, EncryptionService encryptionService, JWTService jwtService,
                       EmailService emailService, VerificationTokenRepository verificationTokenRepository) {
        this.userRepository = userRepository;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
        this.emailService = emailService;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    public ResponseDTO registerUser(UserDTO userDTO) throws EmailFailureException {
        Optional<MarketUser> existingUserResponse = checkForExistingUser(userDTO);
        if(existingUserResponse.isPresent()) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST, "Email or Name already exists");
        }
        MarketUser user = new MarketUser();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(encryptionService.encryptPassword(userDTO.getPassword()));
        VerificationToken verificationToken = createVerificationToken(user);
        emailService.sendVerificationEmail(verificationToken);
        userRepository.save(user);
        return new ResponseDTO(HttpStatus.CREATED, "User registered successfully");
    }

    private VerificationToken createVerificationToken(MarketUser user){
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(jwtService.generateVerificationJWT(user));
        verificationToken.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
        verificationToken.setUser(user);
        user.getVerificationTokens().add(verificationToken);
        return verificationToken;
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

    public String loginUser(LoginBodyDTO loginDTO) throws UserNotVerifiedException, EmailFailureException {
        Optional<MarketUser> existingUser = userRepository.findByNameIgnoreCase(loginDTO.getUsername());
        if(existingUser.isPresent()){
            if(encryptionService.verifyPassword(loginDTO.getPassword(),existingUser.get().getPassword())){
                if(existingUser.get().getEmailVerified()) {
                    return jwtService.generateJWT(existingUser.get());
                }else{
                    List<VerificationToken> verificationTokens = existingUser.get().getVerificationTokens();
                    boolean resend = verificationTokens.size() == 0 ||
                            verificationTokens.get(0).getCreatedTimestamp().before(new Timestamp(System.currentTimeMillis() - (60*60*1000)));
                    if(resend){
                        VerificationToken verificationToken = createVerificationToken(existingUser.get());
                        verificationTokenRepository.save(verificationToken);
                        emailService.sendVerificationEmail(verificationToken);
                    }
                    throw new UserNotVerifiedException(resend);
                }
            }
        }
        return null;
    }

    public Optional<MarketUser> findByUserName(String name){
        return userRepository.findByNameIgnoreCase(name);
    }

    @Transactional
    public boolean verifyUser(String token) {
        Optional<VerificationToken> opToken = verificationTokenRepository.findByToken(token);
        if (opToken.isPresent()) {
            VerificationToken verificationToken = opToken.get();
            MarketUser user = verificationToken.getUser();
            if (!user.getEmailVerified()) {
                user.setEmailVerified(true);
                userRepository.save(user);
                verificationTokenRepository.deleteByUser(user);
                return true;
            }
        }
        return false;
    }
}
