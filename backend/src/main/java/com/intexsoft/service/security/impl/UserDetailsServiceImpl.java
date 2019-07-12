package com.intexsoft.service.security.impl;

import com.intexsoft.dao.model.Role;
import com.intexsoft.dao.model.User;
import com.intexsoft.security.model.UserDetailsImpl;
import com.intexsoft.service.entityservice.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
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

        User user = userService.getUserWithRoles(username).orElseThrow(() ->
                new UsernameNotFoundException("No user found with username: " + username));

        return new UserDetailsImpl(user.getId(), user.getUsername(), user.getPassword(),
                getAuthorities(user.getRoles()));
    }

    private List<GrantedAuthority> getAuthorities(Set<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
