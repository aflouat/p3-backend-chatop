package fr.tmsconsult.p3_backend_chatop.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RentalDTO {
    private int id;
    private String name;
    private float surface;


    private float price;
    private String picture;

    private String description;

    private String createdAt;
    private String updatedAt;
}
