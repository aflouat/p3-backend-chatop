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
public class RentalDTO {
    private int id;
    private String name;
    private float surface;
    private float price;
    private String picture;
    private String description;
    @JsonProperty("created_at")

    private String createdAt;
    @JsonProperty("updated_at")

    private String updatedAt;
    @JsonProperty("owner_id")

    private int ownerId;


}
