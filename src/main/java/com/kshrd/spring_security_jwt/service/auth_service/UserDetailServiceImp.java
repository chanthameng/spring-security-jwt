package com.kshrd.spring_security_jwt.service.auth_service;

import com.kshrd.spring_security_jwt.dto.request.AuthRequest;
import com.kshrd.spring_security_jwt.model.AppUser;
import com.kshrd.spring_security_jwt.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class UserDetailServiceImp implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserDetailServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public AppUser registerNewUser(AuthRequest authRequest) {
        AppUser appUser = new AppUser();
        appUser.setEmail(authRequest.getUsername());
        appUser.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        return userRepository.save(appUser);
    }
}
