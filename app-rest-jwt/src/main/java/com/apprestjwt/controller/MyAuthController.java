package com.apprestjwt.controller;

import com.apprestjwt.loginDto.LoginDto;
import com.apprestjwt.security.JwtProvider;
import com.apprestjwt.service.MyAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class MyAuthController {

    @Autowired
    MyAuthService myAuthService;
    @Autowired
    JwtProvider jwtProvider;


    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/login")
    private HttpEntity loginPage(@RequestBody LoginDto loginDto){

//        UserDetails userDetails = myAuthService.loadUserByUsername(loginDto.getUsername());
//        boolean matches = passwordEncoder.matches(loginDto.getUsername(), userDetails.getUsername());
//        boolean matches = loginDto.getUsername().equals(userDetails.getUsername());
//        if (matches) {
//            String token = jwtProvider.generateToken(loginDto.getUsername());
//            return ResponseEntity.ok(token);
////        }
//            return ResponseEntity.status(401).body("password not found");

        //authenticationManager bilan ishlaganda ozi loadUserByUsernameda login parol bilan userdan kelgan login parolni solishtiradi
        //tepadagidek equal qilish shartmas, bazada  bolmasa throw tashidi
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            String token = jwtProvider.generateToken(loginDto.getUsername());
            return ResponseEntity.ok(token);
        }catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("password not found");
        }

    }

}
