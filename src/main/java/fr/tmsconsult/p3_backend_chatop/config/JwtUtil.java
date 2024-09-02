package fr.tmsconsult.p3_backend_chatop.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    public String extractTokenFromRequest(HttpServletRequest request) {
        String token="";
        String authHeader = request.getHeader("Authorization");
        System.out.println("authHeader: " + authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            token = authHeader.substring(7);
        }
        return token;
    }
}
