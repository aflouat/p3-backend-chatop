package fr.tmsconsult.p3_backend_chatop.security.model;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;

@Component
@Data @NoArgsConstructor @AllArgsConstructor @Getter
public class Token {
    @Value("${jwt.secret}")
    private String secret;

    private SecretKey key;


    @PostConstruct
    public void init() {
        System.out.println("Clé secrète  : " + key);

        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        System.out.println("Clé secrète décodée : " + Base64.getEncoder().encodeToString(this.key.getEncoded()));

    }

}
