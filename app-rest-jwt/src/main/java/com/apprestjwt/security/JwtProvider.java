package com.apprestjwt.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component  // bean qiladi Autowired qilish mn boladi keyin
public class JwtProvider {

    static long expireTime=36000000; //36 ming secund degani bu (1000 =1 second)
    static String kalitSoz = "BuTokenniKalitSoziHechKimBilmasin123456789";

    public String generateToken(String userName){

        Date expireDate = new Date(System.currentTimeMillis() + expireTime);

        String token = Jwts
                .builder()
                .setSubject(userName)   // har bir userni unique narsasi
                .setIssuedAt(new Date()) // token qachon berilgani
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS512, kalitSoz)
                .compact();
        return token;
    }

    public boolean validateToken(String token){
       try {
           Jwts
                   .parser()
                   .setSigningKey(kalitSoz)
                   .parseClaimsJws(token);
           return true;
       }catch (Exception e){
           e.printStackTrace();
       }
       return false;
    }

    public String getUserNameFromToken(String token){
        String subject = Jwts
                .parser()
                .setSigningKey(kalitSoz)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return subject;
    }


}
