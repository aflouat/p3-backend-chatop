package fr.tmsconsult.p3_backend_chatop.services.interfaces;


import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

public interface IJwtService {

    String generateToken(String username);

    String extractEmail(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimResolver);

    Claims extractAllClaims(String token);


    boolean isTokenExpired(String token);

    Date extractExpiration(String token);

    SecretKey getKey();
     String extractTokenFromRequest(HttpServletRequest request);
}
