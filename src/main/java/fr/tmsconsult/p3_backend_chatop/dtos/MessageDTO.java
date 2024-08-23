package fr.tmsconsult.p3_backend_chatop.dtos;

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
    private int userId;
    private int rentalId;
}
