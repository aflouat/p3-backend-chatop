package fr.tmsconsult.p3_backend_chatop.security.service;

import fr.tmsconsult.p3_backend_chatop.security.model.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
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
}
