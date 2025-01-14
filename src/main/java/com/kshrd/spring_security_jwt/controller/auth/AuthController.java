package com.kshrd.spring_security_jwt.controller.auth;

import com.kshrd.spring_security_jwt.config.JwtUtils;
import com.kshrd.spring_security_jwt.dto.request.AuthRequest;
import com.kshrd.spring_security_jwt.dto.response.AuthResponse;
import com.kshrd.spring_security_jwt.model.AppUser;
import com.kshrd.spring_security_jwt.service.auth_service.UserDetailService;
import com.kshrd.spring_security_jwt.service.auth_service.UserDetailServiceImp;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final UserDetailServiceImp userDetailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserDetailServiceImp userDetailService, BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userDetailService = userDetailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    //check user in spring context to ensure user authenticat success or not
    private void authenticate(String username, String password) throws Exception {
        try {
            UserDetails userDetails = userDetailService.loadUserByUsername(username);
            if (userDetails == null) {
                throw new BadRequestException("Wrong username or password");
            }

            if (!bCryptPasswordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadRequestException("Wrong password");
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) throws Exception {
        authenticate(authRequest.getUsername(), authRequest.getPassword());
        final UserDetails userDetails = userDetailService.loadUserByUsername(authRequest.getUsername());
        final String token = jwtUtils.generateToken(userDetails);
        AuthResponse authResponse = new AuthResponse(token);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest authRequest) throws Exception {
        AppUser appUser = userDetailService.registerNewUser(authRequest);
        return ResponseEntity.ok(appUser);

    }


}





