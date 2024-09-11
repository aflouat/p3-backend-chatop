package fr.tmsconsult.p3_backend_chatop.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Data @ToString
@Getter
@Schema(description = "Register request object")

public class RegisterRequest extends LoginRequest {
    @Schema(description = "User's name", example = "John Doe")

    private String name;


    public Object getUserName() {
        return name;
    }
}
