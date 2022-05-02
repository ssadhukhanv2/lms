package com.ssadhukhanv2.lms.librarymanagementsystem.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Subhrajit Sadhukhan
 */
@RestController
@RequestMapping("/jwt")
public class JwtController {

    @GetMapping
    public Jwt jwt(@AuthenticationPrincipal Jwt jwt) {
        return jwt;
    }
}
