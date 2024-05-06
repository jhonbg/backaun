package com.backaun.aplicacion;

import com.backaun.core.dominio.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;
    public String getToken(User user)
    {
        return getToken(new HashMap<>(), user);
    }

    private String getToken(Map<String, Object> extraClaims, User user)
    {
        return Jwts
                .builder()
                .claims(extraClaims)
                .claim("userId",user.getUserId())
                .claim("userIdType",user.getUserIdTipe())
                .claim("userId",user.getUserId())
                .claim("userIdType",user.getUserIdTipe())
                .claim("userName",user.getUserName())
                .claim("userLastname",user.getUserLastname())
                .claim("userPhoneNumber",user.getUserPhoneNumber())
                .claim("roleId",user.getRoleId())
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey()
    {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUserEmailFromToken(String token)
    {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails)
    {
        final String usesEmail= getUserEmailFromToken(token);
        return (usesEmail.equals(userDetails.getUsername())&& !isTokenExpitation(token));
    }

    private Claims getAllClaims(String token)
    {
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver)
    {
        final Claims claims= getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token)
    {
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpitation(String token)
    {
        return getExpiration(token).before(new Date());
    }
}
