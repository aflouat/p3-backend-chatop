package fr.tmsconsult.p3_backend_chatop.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.tmsconsult.p3_backend_chatop.dtos.Responses.AllRentalsDTO;
import fr.tmsconsult.p3_backend_chatop.dtos.requests.RentalDTO;
import fr.tmsconsult.p3_backend_chatop.dtos.requests.RentalDTORequestParam;
import fr.tmsconsult.p3_backend_chatop.entities.Rental;
import fr.tmsconsult.p3_backend_chatop.models.DeterministicDateProvider;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class RentalMapper {
    public String convertRentalDTOListToJSON(List<RentalDTO> rentalsDTO) throws JsonProcessingException {
        return new ObjectMapper().
                writeValueAsString(
                new AllRentalsDTO(rentalsDTO));
    }
    public List<RentalDTO> convertRentalListToRentalDTOList(List<Rental> rentals) {
        List<RentalDTO> rentalsDTO = rentals.stream()
                .map(this::convertRentalToRentalDTO)
                .collect(Collectors.toList());
        return rentalsDTO;
    }
    public RentalDTO convertRentalToRentalDTO(Rental rental) {
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

    public Rental convertRentalRequestParamToRental(RentalDTORequestParam rentalDTORequestParam) throws IOException {
        String uploadDir = System.getProperty("user.dir") + "/uploads/";

        File uploadDirectory = new File(uploadDir);
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();  // Create the directory if it doesn't exist
        }
        File uploadFile = new File(uploadDir + rentalDTORequestParam.getPicture().getOriginalFilename());
        rentalDTORequestParam.getPicture().transferTo(uploadFile);
        String fileUrl = "http://localhost:3001/uploads/" + rentalDTORequestParam.getPicture().getOriginalFilename();

        return new Rental(0,
                rentalDTORequestParam.getName(),
                rentalDTORequestParam.getSurface(),
                rentalDTORequestParam.getPrice(),
                fileUrl,
                rentalDTORequestParam.getDescription(),
                new DeterministicDateProvider().now(),
                new DeterministicDateProvider().now(),
                1 //Todo ownerId
                );
    }
    public String convertOneToJSON(RentalDTO rentalDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(rentalDTO);
    }

}
