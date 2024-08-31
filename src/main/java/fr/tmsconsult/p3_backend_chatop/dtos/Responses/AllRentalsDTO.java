package fr.tmsconsult.p3_backend_chatop.dtos.Responses;

import fr.tmsconsult.p3_backend_chatop.dtos.requests.RentalDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Data
@Getter
@Setter
@AllArgsConstructor
public class AllRentalsDTO {
    private List<RentalDTO> rentals;

}
