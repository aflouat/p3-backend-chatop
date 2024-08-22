package fr.tmsconsult.p3_backend_chatop.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.tmsconsult.p3_backend_chatop.dtos.RentalDTO;
import fr.tmsconsult.p3_backend_chatop.entities.Rental;
import fr.tmsconsult.p3_backend_chatop.models.DeterministicDateProvider;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
                rental.getUpdatedAt().toString(),
                rental.getOwnerId()

        );
    }

    public Rental getOneFromRequest(String name,
                                    float surface,
                                    float price,
                                    MultipartFile picture,
                                    String description) throws IOException {

        // Save the MultipartFile to a temporary file
        File tempFile = File.createTempFile("upload", picture.getOriginalFilename());
        picture.transferTo(tempFile);
        return new Rental(0,
                name,
                surface,
                price,
                tempFile.getName(),
                description,
                new DeterministicDateProvider().now(),
                new DeterministicDateProvider().now(),1 //ownerId
                );
    }


}
