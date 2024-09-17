package fr.tmsconsult.p3_backend_chatop.dtos.Responses;

import fr.tmsconsult.p3_backend_chatop.dtos.requests.RentalDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
@RequiredArgsConstructor
@Getter
@Setter
public class RentalResponseList {
    private final List<RentalDTO> rentals;
}
