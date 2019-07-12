package com.intexsoft.web.controllers;

import com.intexsoft.dao.model.User;
import com.intexsoft.service.entityservice.RoleService;
import com.intexsoft.service.entityservice.UserService;
import com.intexsoft.service.security.TokenService;
import com.intexsoft.web.dto.request.UserAuthorizDTO;
import com.intexsoft.web.dto.request.UserRequestDTO;
import com.intexsoft.web.dto.response.TokenDTO;
import com.intexsoft.web.dto.response.UserResponseDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    private static final Logger LOGGER = LogManager.getLogger(AuthenticationController.class);

    private final UserService userService;
    private final Mapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final RoleService roleService;

    public AuthenticationController(UserService userService, Mapper mapper,
                                    PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                                    TokenService tokenService, RoleService roleService) {
        this.userService = userService;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.roleService = roleService;
    }

    @PostMapping("/sing-up")
    public ResponseEntity<UserResponseDTO> signUp(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        User user = mapper.map(userRequestDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRequestDTO.getRoles().stream().map(name ->
                roleService.findByName(name).orElseThrow(() ->
                        new RuntimeException("role with the given name not found")
                )).collect(Collectors.toSet()));
        user = userService.save(user);
        LOGGER.info("User with username {} was registered", user.getUsername());
        return ResponseEntity.ok(mapper.map(user, UserResponseDTO.class));
    }

    @PostMapping("/sing-in")
    public ResponseEntity<TokenDTO> signIn(@Valid @RequestBody UserAuthorizDTO userAuthorizDTO) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(userAuthorizDTO.getUsername(), userAuthorizDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(new TokenDTO(tokenService.generate(authentication)));
    }
}
