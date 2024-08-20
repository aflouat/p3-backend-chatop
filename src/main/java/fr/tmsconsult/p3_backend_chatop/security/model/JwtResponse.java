package fr.tmsconsult.p3_backend_chatop.security.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "JWT response object")

public class JwtResponse {
    @Schema(description = "JWT token")

    private String token;


}
