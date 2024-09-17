package fr.tmsconsult.p3_backend_chatop.config;


import fr.tmsconsult.p3_backend_chatop.services.impl.UserDetailsServiceImpl;
import fr.tmsconsult.p3_backend_chatop.services.interfaces.IJwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    public static final String LOGIN_PATH = "/api/auth/login";
    public static final String REGISTER_PATH = "/api/auth/register";
    public static final String ERROR_BAD_CREDENTIALS = "Error: Bad credentials";
    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    private final IJwtService jwtServiceImpl;
    private final ApplicationContext context;
    private final IJwtService jwtService;

    private static boolean shouldAuthenticate(String username) {
        return username != null && SecurityContextHolder.getContext().getAuthentication() == null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getServletPath();

        if (path.equals(LOGIN_PATH) || path.equals(REGISTER_PATH)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = jwtService.extractTokenFromRequest(request);
        String email = null;
        if (StringUtils.hasText(token)) {
            email = jwtServiceImpl.extractEmail(token);
        }
        if (shouldAuthenticate(email)) {

            try {
                UserDetails userDetails = context.getBean(UserDetailsServiceImpl.class).loadUserByUsername(email);
                if (userDetails != null) {
                    if (!jwtServiceImpl.isTokenExpired(token)) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken
                                (userDetails, null, userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            } catch (ExpiredJwtException e) {
                logger.error("Token expired", e);

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token expired");
            }catch (MalformedJwtException e) {
            logger.error("Token malformed", e);

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token malformed");
        }catch (Exception e) {
                logger.error("Authentication error", e);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Authentication failed");
            }

        }
        filterChain.doFilter(request, response);
    }
}
