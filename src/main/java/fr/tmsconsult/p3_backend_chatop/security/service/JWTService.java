package fr.tmsconsult.p3_backend_chatop.security.service;

import fr.tmsconsult.p3_backend_chatop.security.model.Token;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
@Service
public class JWTService {
    @Autowired
    private Token token;



    // Méthode pour valider et extraire les claims du JWT
    public  Claims getClaimsFromToken(String tokenStr) {

        try {
            return Jwts.parser()
                    .setSigningKey(this.token.getKey()) // Utiliser la même clé secrète pour vérifier la signature
                    .build()
                    .parseClaimsJws(tokenStr)
                    .getBody();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


    public   String findEmailByToken(String token) {
        String jwt = token.replace("Bearer ", "");

        // Valider et parser le JWT pour obtenir les claims
        Claims claims = this.getClaimsFromToken(jwt);
        String email = claims.get("email", String.class);
        return email;
    }

    public void checkJWTValidity(String token) throws SecurityException {
        String jwt = token.replace("Bearer ", "");
        try {
            // Validate the JWT
            Jwts.parser()
                    .setSigningKey(this.token.getKey()) // Use the same secret key to verify the signature
                    .build()
                    .parseClaimsJws(jwt);
        } catch (ExpiredJwtException e) {
            throw new SecurityException("JWT token has expired", e);
        } catch (SignatureException e) {
            throw new SecurityException("Invalid JWT signature", e);
        } catch (MalformedJwtException e) {
            throw new SecurityException("Malformed JWT token", e);
        } catch (UnsupportedJwtException e) {
            throw new SecurityException("Unsupported JWT token", e);
        } catch (IllegalArgumentException e) {
            throw new SecurityException("JWT token is missing or incorrect", e);
        }


    }
}
