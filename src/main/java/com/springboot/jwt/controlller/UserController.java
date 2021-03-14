package com.springboot.jwt.controlller;

import com.springboot.jwt.entity.UserEntity;
import com.springboot.jwt.model.JWTRequest;
import com.springboot.jwt.model.JWTResponse;
import com.springboot.jwt.repository.UserRepository;
import com.springboot.jwt.service.UserService;
import com.springboot.jwt.utility.JWTUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add-user")
    public UserEntity createUser(@RequestBody UserEntity userEntity) {
        log.info("Saving the new user with user name : " + userEntity.getUserName());
        return userRepository.save(userEntity);
    }

    @GetMapping("/restricted-access-rest-endpoint")
    public String restrictedAccessEndpoint() {
        return "Welcome to the demo of JWT with Spring Boot!!!!";
    }

    @PostMapping("/authenticate")
    public JWTResponse authenticate(@RequestBody JWTRequest jwtRequest) throws Exception {
        try {
            authenticationManager.authenticate
                    (new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getPassword()));

        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUserName());
        final String token = jwtUtility.generateToken(userDetails);
        return new JWTResponse(token);
    }
}
