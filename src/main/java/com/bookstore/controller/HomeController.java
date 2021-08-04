package com.bookstore.controller;

import com.bookstore.model.jwt.JwtRequest;
import com.bookstore.model.jwt.JwtResponse;
import com.bookstore.security.jwt.JwtTokenUtil;
import com.bookstore.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/bookstore/v1")
public class HomeController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @GetMapping(value = "/")
    public String hello(){
        return "Hello Everyone";
    }

    @GetMapping("/user")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/mod")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    public String adminAccess() {
        return "Admin Board.";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody JwtRequest jwtRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        } catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password", e);
        }
        final var userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        final String token = jwtTokenUtil.generateJwtToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
