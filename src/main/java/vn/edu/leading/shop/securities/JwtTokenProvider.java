package vn.edu.leading.shop.securities;


import io.jsonwebtoken.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExprirationInMs}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication)
    {
        User user = (User) authentication.getPrincipal();

        Date now = new Date();

        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
    }

    public String getUsernameFromJWT(String token)
    {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Boolean validateToken(String autoToken)
    {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(autoToken);
            return true;
        }
        catch (SignatureException ex) {
            LOGGER.error("Invalid JWT signature");
        }
        catch (MalformedJwtException ex) {
            LOGGER.error("Invalid JWT token");
        }
        catch (ExpiredJwtException ex) {
            LOGGER.error("Expired JWT token");
        }
        catch (UnsupportedJwtException ex) {
            LOGGER.error("Unsupported JWT token");
        }
        catch (IllegalArgumentException ex) {
            LOGGER.error("JWT claims string is empty.");
        }
        return false;
    }

}
