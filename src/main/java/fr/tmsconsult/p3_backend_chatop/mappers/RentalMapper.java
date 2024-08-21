package fr.tmsconsult.p3_backend_chatop.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.tmsconsult.p3_backend_chatop.dtos.RentalDTO;
import fr.tmsconsult.p3_backend_chatop.entities.Rental;

import java.util.List;
import java.util.stream.Collectors;

public class RentalMapper {
    public String getDTOAsJSON(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
    public List<RentalDTO> getDTOFromRentals(List<Rental> rentals) {
        List<RentalDTO> rentalsDTO = rentals.stream()
                .map(this::getDTOFromRental)
                .collect(Collectors.toList());
        return rentalsDTO;
    }
    public RentalDTO getDTOFromRental(Rental rental) {
        return new RentalDTO(
                rental.getId(),
                rental.getName(),
                rental.getSurface(),
                rental.getPrice(),
                rental.getPicture(),
                rental.getDescription(),
                rental.getCreatedAt().toString(),
                rental.getUpdatedAt().toString()
        );
    }

}
