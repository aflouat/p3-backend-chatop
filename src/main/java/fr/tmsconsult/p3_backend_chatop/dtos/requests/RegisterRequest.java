package fr.tmsconsult.p3_backend_chatop.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Data @ToString
@Getter
@Schema(description = "Register request object")

public class RegisterRequest {

    @Schema(description = "User's email", example = "user@example.com")
    private String email;
    @Schema(description = "User's password", example = "P@ssw0rd!")
    private String password;
    @Schema(description = "User's name", example = "John Doe")
    private String name;


}
