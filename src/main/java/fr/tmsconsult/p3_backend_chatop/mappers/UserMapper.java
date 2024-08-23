package fr.tmsconsult.p3_backend_chatop.mappers;

import fr.tmsconsult.p3_backend_chatop.entities.User;
import fr.tmsconsult.p3_backend_chatop.security.model.RegisterRequest;

import java.time.LocalDateTime;

public class UserMapper {
    private User mapToUser(RegisterRequest registerRequest) {
        return new User(0,registerRequest.getName(), registerRequest.getLogin(),
                registerRequest.getPassword(), LocalDateTime.now(),LocalDateTime.now() );
    }
}
