package fr.tmsconsult.p3_backend_chatop.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CurrentUserDTO {

    private Integer id;
    private String name;
    private String email;
    private String createdAt;
    private String updatedAt;
}
