package fr.tmsconsult.p3_backend_chatop.config;


import fr.tmsconsult.p3_backend_chatop.services.impl.JWTService;
import fr.tmsconsult.p3_backend_chatop.services.impl.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {


    private final JWTService jwtService;

    private final ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractTokenFromRequest(request);
        String username = null;
        if (token!=null && !token.isEmpty()){
            username = jwtService.extractUserName(token);
        }
        updateSecurityContextWithJwtAuthentication(request, username, token);
        filterChain.doFilter(request, response);
    }

    private void updateSecurityContextWithJwtAuthentication(HttpServletRequest request, String username, String token) {
        if (isLoginRequest(username)) {
            UserDetails userDetails = context.
                    getBean(MyUserDetailsService.class).
                    loadUserByUsername(username);
            validateToken(request, token, userDetails);
        }
    }

    private void validateToken(HttpServletRequest request, String token, UserDetails userDetails) {
        if (jwtService.validateToken(token, userDetails)) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource()
                    .buildDetails(request));
            SecurityContextHolder.
                    getContext().
                    setAuthentication(authToken);
        }
    }



    private String extractTokenFromRequest(HttpServletRequest request) {
        String token="";
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            token = authHeader.substring(7);
        }
        return token;
    }

    private static boolean isLoginRequest(String username) {
        return username != null && SecurityContextHolder.getContext().getAuthentication() == null;
    }
}
