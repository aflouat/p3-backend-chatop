package fr.tmsconsult.p3_backend_chatop.dtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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
