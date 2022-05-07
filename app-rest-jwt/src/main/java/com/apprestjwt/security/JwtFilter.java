package com.apprestjwt.security;

import com.apprestjwt.service.MyAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    MyAuthService myAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //requestdan tokenni olish
        String token = request.getHeader("Authorization");

        //tokenni borligini va bearer bilan boshlanishini tekshirish
        if (token.startsWith("Barier") && token != null){

            // tokenni ozini qirqib olish(Bearer ni olib tashlash boshidan)
            token=token.substring(7);

            //tokenni tekshirish(buzilmaganligi, muddati otganligini)
            boolean validateToken = jwtProvider.validateToken(token);
            if (validateToken){

                //tokenni ichidan usernameni oldik
                String username = jwtProvider.getUserNameFromToken(token);

                //username orqali userdetails ni oldik
                UserDetails userDetails = myAuthService.loadUserByUsername(username);

                //userdetailts orqali authentication yaratib oldik
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                System.out.println(SecurityContextHolder.getContext().getAuthentication());

                //sistemaga kim kirganini ornatdik
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            }

        }

        filterChain.doFilter(request,response);
    }
}
