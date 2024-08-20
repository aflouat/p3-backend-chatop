package fr.tmsconsult.p3_backend_chatop.services;

import fr.tmsconsult.p3_backend_chatop.entities.User;
import fr.tmsconsult.p3_backend_chatop.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import java.util.Date;


@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    @Autowired
    UserRepository userRepository;

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
                .compact();
    }

    public String hashPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }
    public boolean checkPassword(String plainPassword, String hashedPassword) {

        return passwordEncoder.matches(plainPassword, hashedPassword);
    }
}
