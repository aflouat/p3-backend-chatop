package fr.tmsconsult.p3_backend_chatop.services.impl;

import fr.tmsconsult.p3_backend_chatop.entities.Rental;
import fr.tmsconsult.p3_backend_chatop.repositories.RentalRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;
    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

   public Rental findRentalById(Integer id) {
        return  rentalRepository.findById(id).orElse(null);
    }

    public void addRental(Rental rental) {
        rentalRepository.save(rental);
    }

    public void updateRental(Rental rentalToUpdate) {
        // Check if the rental exists by its ID
        Rental existingRental = rentalRepository.findById(rentalToUpdate.getId())
                .orElseThrow(() -> new EntityNotFoundException("Rental not found"));

        // Update the fields of the existing rental
        existingRental.setName(rentalToUpdate.getName());
        existingRental.setSurface(rentalToUpdate.getSurface());
        existingRental.setPrice(rentalToUpdate.getPrice());
        existingRental.setPicture(rentalToUpdate.getPicture());
        existingRental.setDescription(rentalToUpdate.getDescription());
        existingRental.setUpdatedAt(LocalDate.now());
        // Save the updated rental back to the repository
        rentalRepository.save(existingRental);
    }
}
