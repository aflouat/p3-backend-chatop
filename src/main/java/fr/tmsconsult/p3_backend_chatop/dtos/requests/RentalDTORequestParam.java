package fr.tmsconsult.p3_backend_chatop.dtos.requests;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RentalDTORequestParam {
    private int id;
    private String name;
    private float surface;
    private float price;
    private MultipartFile picture;
    private String description;

    public void setId(Integer id) {
        this.id = id;
    }
}
