package fr.tmsconsult.p3_backend_chatop.services;

import fr.tmsconsult.p3_backend_chatop.dtos.RentalDTO;
import fr.tmsconsult.p3_backend_chatop.entities.Rental;
import fr.tmsconsult.p3_backend_chatop.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalService {
    @Autowired
    RentalRepository rentalRepository;


    public List<RentalDTO> getAllRentals() {
        return rentalRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private RentalDTO convertToDTO(Rental rental) {
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
