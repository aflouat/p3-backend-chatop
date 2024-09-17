package fr.tmsconsult.p3_backend_chatop.services.interfaces;

import fr.tmsconsult.p3_backend_chatop.entities.Rental;

import java.util.List;

public interface IRentalService {
    Rental findRentalById(Integer id);
    List<Rental> getAllRentals();
    void addRental(Rental rental);
    void updateRental(Rental rentalToUpdate);
}
