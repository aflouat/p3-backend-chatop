package fr.tmsconsult.p3_backend_chatop.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MessageDTO {

    private String message;
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("rental_id")
    private int rentalId;
}
