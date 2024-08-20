package fr.tmsconsult.p3_backend_chatop.services;

import fr.tmsconsult.p3_backend_chatop.entities.User;
import fr.tmsconsult.p3_backend_chatop.repositories.UserRepository;
import fr.tmsconsult.p3_backend_chatop.security.model.Token;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import java.time.LocalDateTime;
import java.util.Date;


@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    @Autowired
    UserRepository userRepository;

    @Autowired
    private Token token;
    //private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Génère une clé secrète sécurisée

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public String login(String email, String password) throws Exception {
        User user = userRepository.findByEmail(email);
        logger.info("Password verified for user: {}", email);

        if (user == null || !this.checkPassword(password, user.getPassword())) {
            throw new Exception("Invalid email or password");
        }
        return generateToken(user);
    }

    public String generateToken(User user) {
        logger.info("Generated token for user: {}", user.getEmail());

        return Jwts.builder()
                .claim("name",user.getName())
                .claim("email",user.getEmail())
                .claim("id", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 heures de validité
                .signWith(token.getKey()) // Signer le token avec la clé
                .compact();
    }

    public String hashPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }
    public boolean checkPassword(String plainPassword, String hashedPassword) {

        return passwordEncoder.matches(plainPassword, hashedPassword);
    }

    public User register(String name, String login, String password, LocalDateTime created,LocalDateTime updated ) {
        User user = new User(null,login,name,passwordEncoder.encode(password),created,updated);
        User saved = userRepository.save(user);
        return saved;
    }

    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user;
    }




}
