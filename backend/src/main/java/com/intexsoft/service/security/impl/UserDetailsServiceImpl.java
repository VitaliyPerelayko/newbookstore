package com.intexsoft.service.security.impl;

import com.intexsoft.dao.model.User;
import com.intexsoft.security.model.UserDetailsImpl;
import com.intexsoft.service.entityservice.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userService.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("No user found with username: " + username));
        return new UserDetailsImpl(user.getId(), user.getUsername(), user.getPassword(),
                //TODO: if i add roles in database rewrite this part
                getAuthorities(Collections.singletonList("ROLE_ADMIN")));
    }

    private List<GrantedAuthority> getAuthorities(List<String> roles) {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
