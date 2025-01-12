package com.kshrd.spring_security_jwt.service;

import com.kshrd.spring_security_jwt.model.MyUserDetail;
import com.kshrd.spring_security_jwt.model.Users;
import com.kshrd.spring_security_jwt.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUsername(username);

        if (users == null) {
            System.out.println("user not found");
            throw new UsernameNotFoundException("user not found");
        }
        return new MyUserDetail(users);
    }
}
