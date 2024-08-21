package fr.tmsconsult.p3_backend_chatop.services;

import fr.tmsconsult.p3_backend_chatop.dtos.RentalDTO;
import fr.tmsconsult.p3_backend_chatop.entities.Rental;
import fr.tmsconsult.p3_backend_chatop.repositories.FakeRentalRepository;
import fr.tmsconsult.p3_backend_chatop.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;


    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }



    public Rental findRentalById(Integer id) {
        return  rentalRepository.findById(id).orElse(null);
    }
}
