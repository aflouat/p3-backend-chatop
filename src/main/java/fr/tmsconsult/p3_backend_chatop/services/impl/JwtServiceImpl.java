package fr.tmsconsult.p3_backend_chatop.services.impl;

import fr.tmsconsult.p3_backend_chatop.services.interfaces.IJwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements IJwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);
    public static final String KEY_GEN_ALGORITHM = "HmacSHA256";
    @Value("${jwt.secret}")
    private String secretKey ;
    public JwtServiceImpl() {

        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(KEY_GEN_ALGORITHM);
            SecretKey sk = keyGen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        String token  = Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 30* 1000))
                .and()
                .signWith(getKey())
                .compact();
        logger.info("generated token: " + token);
        return token;

    }

    public SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractEmail(String token) throws MalformedJwtException{
        return extractClaim(token, Claims::getSubject);
    }

    public  <T> T extractClaim(String token, Function<Claims, T> claimResolver) throws MalformedJwtException {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) throws MalformedJwtException{

        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }



    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public String extractTokenFromRequest (HttpServletRequest request) throws MalformedJwtException  {
        String token = "";
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            token = authHeader.substring(7);
        }
        if (!StringUtils.hasText(token)){
            throw new MalformedJwtException("Invalid JWT token");
        }
        return token;
    }
}
