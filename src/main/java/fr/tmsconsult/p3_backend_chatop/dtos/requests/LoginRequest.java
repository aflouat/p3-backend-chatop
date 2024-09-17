package fr.tmsconsult.p3_backend_chatop.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Schema(description = "Login request object")

public class LoginRequest {
    @Schema(description = "User's email", example = "user@example.com")

    private String login;
    @Schema(description = "User's password", example = "P@ssw0rd!")
    private String password;

}
