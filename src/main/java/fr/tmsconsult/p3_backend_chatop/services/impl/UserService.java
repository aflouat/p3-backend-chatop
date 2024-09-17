package fr.tmsconsult.p3_backend_chatop.services.impl;

import fr.tmsconsult.p3_backend_chatop.entities.User;
import fr.tmsconsult.p3_backend_chatop.repositories.UserRepo;
import fr.tmsconsult.p3_backend_chatop.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {


    public static  String FAILD_USER_AUTHENTICATION = "faild user authentication";
    private final JwtServiceImpl jwtServiceImpl;
    private final AuthenticationManager authManager;
    private final UserRepo repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    @Override
    public Optional<User> fetchUserByEmail(String email) {
        return repo.findByEmail(email);
    }

    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);

    }
    public String generateToken(String email) {
        return jwtServiceImpl.generateToken(email);
    }

    public String verifyEmailAndPassword(User user)  throws AuthenticationException {
        logger.info("Verifying email and password");
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            logger.info("Successful Authentication!");
            return generateToken(user.getEmail());

    }

    public Optional<User> fetchUserByToken(String token) {
        String email = jwtServiceImpl.extractEmail(token);
        Optional<User> user = repo.findByEmail(email);
        return  user;
    }
}
