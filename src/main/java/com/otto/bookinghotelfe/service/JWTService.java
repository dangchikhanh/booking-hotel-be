package com.otto.bookinghotelfe.service;

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
public class JWTService {

    private static final String SECRET_KEY = "M1YZI4q1zz994BuGIZUHLs8d8xeq/f2HbGRnRM6NbeiYgKH1R7VmJ5XO+ZXrpMOU0VCGBDMaX3aA30zugoJsAh3Q8vq+yYN6VCnxX95DJGxwJmVuZs+eOEutA1hhi43ggM9NCVwK2fDgIkDfzB7B2HkoFO+1r1tyssogd7/x+CQH02cmf7rzy6+75sK5jQLRhduZyS2RvhWBCXGNRDbA8OsVYIZedWsPvAu7F8rNQwsPC8Mi2H8mpcXjpuv3JFCzqTv9B/lTrSIA5r0hxo1jeavzuYSWhwlfw/IrlXx7X6SbpEFkyKodw+3d9oW+AV69Y5wTvJft1vv4XPPDR5rYqhipO557WRlkCRUWNv68GIM=";

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

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals((userDetails.getUsername())) && !isTokenExpried(token));
    }

    public boolean isTokenExpried(String token) {
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
}
