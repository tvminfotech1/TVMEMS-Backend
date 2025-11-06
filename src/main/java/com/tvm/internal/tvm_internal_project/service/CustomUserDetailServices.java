package com.tvm.internal.tvm_internal_project.service;

import com.tvm.internal.tvm_internal_project.model.User;
import com.tvm.internal.tvm_internal_project.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CustomUserDetailServices implements UserDetailsService {

    @Autowired
    private UserRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;

        // Try to find by email
        user = userRepository.findByEmail(username).orElse(null);

        // If not found and username looks like a number, try mobile
        if (user == null && username.matches("\\d+")) {
            Long mobile = Long.parseLong(username);
            user = userRepository.findByMobile(mobile).orElse(null);
        }

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email or mobile: " + username);
        }

        // Set authorities
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // Always use EMAIL as the username for JWT consistency
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail()) // this ensures token always uses email
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }


}