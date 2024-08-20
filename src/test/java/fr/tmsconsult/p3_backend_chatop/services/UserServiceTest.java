package fr.tmsconsult.p3_backend_chatop.services;

import fr.tmsconsult.p3_backend_chatop.entities.User;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.security.SignatureException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest {

    private UserService userService;

    @Value("${jwt.secret}")
    private String secretKey;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }


}
