package com.kshrd.spring_security_jwt.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@SecurityRequirement(name = "bearerAuth")
public class TestController {
    @GetMapping
    public String test() {
        return "test";
    }
}
