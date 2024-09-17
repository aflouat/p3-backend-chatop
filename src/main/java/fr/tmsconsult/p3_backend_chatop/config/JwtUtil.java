package fr.tmsconsult.p3_backend_chatop.config;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    public String extractTokenFromRequest(HttpServletRequest request) {
        String token = "";
        String authHeader = request.getHeader("Authorization");
        logger.debug("authHeader: " + authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            token = authHeader.substring(7);
        }
        return token;
    }
}
