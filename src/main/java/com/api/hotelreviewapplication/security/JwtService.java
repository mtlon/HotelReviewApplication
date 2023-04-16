package com.api.hotelreviewapplication.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY = "743777217A25432A462D4A404E635266556A586E3272357538782F413F442847";
    private Date expireDate = new Date(System.currentTimeMillis() + 1000 * 60 * 24);
    private Date currentDate = new Date(System.currentTimeMillis());
//    private long refreshExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }
    private String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(extractUsername(token));
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
//    public String generateRefreshToken(
//            UserDetails userDetails
//    ) {
//        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
//    }
//    private String buildToken(
//            Map<String, Object> extraClaims,
//            UserDetails userDetails,
//            long expiration
//    ) {
//        return Jwts
//                .builder()
//                .setClaims(extraClaims)
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(currentDate)
//                .setExpiration(expireDate)
//                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }

//    public  Claims getClaimsFromToken(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(getSignInKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }


}
