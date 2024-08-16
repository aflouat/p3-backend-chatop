package fr.tmsconsult.p3_backend_chatop.security.model;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class LoginRequest {
    private String login;
    private String password;

}
