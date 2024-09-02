package fr.tmsconsult.p3_backend_chatop.dtos.Responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {

    private Integer id;
    private String name;
    private String email;
    private String createdAt;
    private String updatedAt;
}
