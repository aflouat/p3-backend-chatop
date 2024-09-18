package fr.tmsconsult.p3_backend_chatop.services.interfaces;

import fr.tmsconsult.p3_backend_chatop.entities.User;

import java.util.Optional;

public interface IUserService {

    User register(User user);

    String verifyEmailAndPassword(User user);

    Optional<User> fetchUserByToken(String token);

    Optional<User> fetchUserByEmail(String email);
    String generateToken(String email);
}