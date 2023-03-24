package com.example.demo.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {


    @Value("${JWT_SECRET}")
    private String SECRET_KEY;

    @Value("${JWT_EXPIRATION_TIME}")
    private int Token_expiry;



    public String extractName(String jwt) {

        return extractClaim(jwt, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T>claimsFunction) {
        final Claims claims = extractAllClaims(token);
        return claimsFunction.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateJWTToken(UserDetails userDetails){
        return generateJWTToken(new HashMap<>(), userDetails);
    }

    public String generateJWTToken(Map<String, Object> extraClaims,UserDetails userDetails){

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + Token_expiry))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean checkIfTokenIsValid(String jwtToken, UserDetails userDetails){
        final String name = extractName(jwtToken);
        return (name.equals(userDetails.getUsername())) && !isTokenExpired(jwtToken);
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractExpirationDate(jwtToken).before(new Date());
    }

    private Date extractExpirationDate(String jwtToken){
        return extractClaim(jwtToken, Claims::getExpiration);
    }


}
