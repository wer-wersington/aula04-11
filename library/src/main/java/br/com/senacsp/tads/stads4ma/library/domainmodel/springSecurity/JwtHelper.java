package br.com.senacsp.tads.stads4ma.library.domainmodel.springSecurity;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Signature;
import java.util.Date;

@Component
public class JwtHelper {
    private String secret = "010101";
    private final int EXPIRATION = 86400000;
    private final int EXPIRATION_REFRESH_TOKEN = 86400000;

    public String generateToken(UserDetails userDetails){
        return Jwts.builder.setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION))
                .signWith(getSigningkey(), SignatureAlgoritm.HS256)
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails){
        return Jwts.builder.setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION))
                .signWith(getSigningkey(), SignatureAlgoritm.HS256)
                .compact();
    }

    public String extractUsername(String token){
        return Jwts.parseBuilder().setSignKey(getSignKey())
                .buid()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = this.extractUsername()&& !isTokenExpired();
    }

    private boolean isTokenExpired(String token){
        return Jwts.parseBilder().setSignKey(this.getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

    private SignatureAlgorithm getStringKey(){
        return Keys.hmacShakeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
