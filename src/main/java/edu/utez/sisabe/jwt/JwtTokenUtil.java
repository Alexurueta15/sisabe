package edu.utez.sisabe.jwt;

import edu.utez.sisabe.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final long JWT_TOKEN_VALIDITY = (long) 5 * 60 * 60;

    private final SecretKey key;

    public JwtTokenUtil(@Value("${key.secret}")
                                byte[] keySecret) {
        this.key = new SecretKeySpec(keySecret, "HmacSHA512");
        //toGenerate the secure byte array
        //this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        //get the byte array
        //this.key.getEncoded();
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public String getRoleFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        if (claims != null) {
            return claims.get("role", String.class);
        }
        return null;
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        if (claims != null) {
            return claimsResolver.apply(claims);
        }
        return null;
    }


    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException
                | SignatureException | IllegalArgumentException e) {
            return null;
        }
    }

    public String generateToken(User user) {
        Map<String, String> claims = new HashMap<>();
        claims.put("role", user.getRole());

        return Jwts.builder().setClaims(claims)
                .setSubject(user.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(key).compact();
    }
}
