package com.intexsoft.web.controllers;

import com.intexsoft.dao.model.User;
import com.intexsoft.service.entityservice.UserService;
import com.intexsoft.web.dto.request.UserRequestDTO;
import com.intexsoft.web.dto.response.UserResponseDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    private static final Logger LOGGER = LogManager.getLogger(AuthenticationController.class);

    private final UserService userService;
    private final Mapper mapper;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationController(UserService userService, Mapper mapper,
                                    ApplicationEventPublisher applicationEventPublisher, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/sing-up")
    public ResponseEntity<UserResponseDTO> signUp(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        User user = mapper.map(userRequestDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userService.save(user);
        LOGGER.info("User with username {} was registered", user.getUsername());
        return ResponseEntity.ok(mapper.map(user, UserResponseDTO.class));
    }
}
