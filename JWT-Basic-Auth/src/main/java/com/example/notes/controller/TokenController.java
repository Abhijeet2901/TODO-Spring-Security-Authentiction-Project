package com.example.notes.controller;

import com.example.notes.model.AuthenticationTokenBody;
import com.example.notes.security.util.JwtUtil;
import com.example.notes.service.UserDetailsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class TokenController {

    @Autowired
    UserDetailsDAO userDetailsDAO;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/token")
    public ResponseEntity<String> getToken(@RequestBody AuthenticationTokenBody request){
        //Authentication Manager will handle all the job of authenticating user,
        // instead of us doing manual authentication using UserDetailsService.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));

        User user = userDetailsDAO.loadUserByEmail(request.getEmail());
        if(!ObjectUtils.isEmpty(user)){
            return ResponseEntity.ok(jwtUtil.generateToken(user));
        }
        return new ResponseEntity<>("Invalid User Deatils!!", HttpStatus.BAD_REQUEST);
    }
}
