package fr.tmsconsult.p3_backend_chatop.config;


import fr.tmsconsult.p3_backend_chatop.services.impl.JwtServiceImpl;
import fr.tmsconsult.p3_backend_chatop.services.impl.MyUserDetailsServiceImpl;
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
    private final JwtServiceImpl jwtServiceImpl;
    private final ApplicationContext context;
    private final JwtUtil jwtUtil;



    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();

        if (path.equals("/api/auth/login") || path.equals("/api/auth/register")) {
            filterChain.doFilter(request, response);
            return;  // Continuer la chaîne sans vérification JWT pour ces routes
        }
        String token = jwtUtil.extractTokenFromRequest(request);
        String email = null;
        System.out.println("token: " + token);
        if (token!=null && !token.isEmpty()){
            email = jwtServiceImpl.extractEmail(token);
        }
        if (hasToBoAuthenticated(email)) {
            System.out.println("login request: " + email);
            UserDetails userDetails = context.
                    getBean(MyUserDetailsServiceImpl.class).
                    loadUserByUsername(email);
            validateToken(request, token, userDetails);
        }
        filterChain.doFilter(request, response);
    }



    private void validateToken(HttpServletRequest request, String token, UserDetails userDetails) {
        boolean isValidToken = jwtServiceImpl.hasTokenNotExpiredAndExistingUser(token, userDetails);
        if (isValidToken) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource()
                    .buildDetails(request));
            SecurityContextHolder.
                    getContext().
                    setAuthentication(authToken);
        }
    }





    private static boolean hasToBoAuthenticated(String username) {
        return username != null && SecurityContextHolder.getContext().getAuthentication() == null;
    }
}
