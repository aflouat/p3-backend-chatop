package fr.tmsconsult.p3_backend_chatop.services;

import fr.tmsconsult.p3_backend_chatop.entities.Rental;
import fr.tmsconsult.p3_backend_chatop.mappers.RentalMapper;
import fr.tmsconsult.p3_backend_chatop.models.DeterministicDateProvider;
import fr.tmsconsult.p3_backend_chatop.repositories.FakeRentalRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RentalServiceTest {
    FakeRentalRepository repo;
    RentalService service;
    RentalMapper rentalMapper;
    private final DeterministicDateProvider fakeDateProvider = new DeterministicDateProvider();


    @BeforeEach
    void setUp() {
        repo = new FakeRentalRepository();
        rentalMapper = new RentalMapper();
        service = new RentalService(repo);
        fakeDateProvider.currentDate = LocalDate.of(2020, 2, 4);
        repo.clear();
    }

    @Test
    void canAddRental() {
        // Given
        Rental rental = new Rental(1, "bel appart", 55, 950, "belle facade", "T2 spacieux et lumineux",
                fakeDateProvider.now(), fakeDateProvider.now(),1);

        // When
        service.addRental(rental);

        // Then
        Rental retrievedRental = service.findRentalById(1);
        assertEquals(
                new Rental(1, "bel appart", 55, 950, "belle facade", "T2 spacieux et lumineux",
                        fakeDateProvider.now(), fakeDateProvider.now(),1),
                retrievedRental
        );
    }

    @SneakyThrows
    @Test
    public void shouldReturnAllRentalsDTOAsJSON(){
        // Given
        Rental rental1 = new Rental(1, "bel appart", 55, 950, "belle facade", "T2 spacieux et lumineux",
                fakeDateProvider.now(), fakeDateProvider.now(),1);
        Rental rental2 = new Rental(2, "modern studio", 40, 750, "vue sur parc", "Studio moderne et bien agencé",
                fakeDateProvider.now(), fakeDateProvider.now(),1);




        // When
        repo.save(rental1);
        repo.save(rental2);
        List<Rental> rentals = service.getAllRentals();
        System.out.println("rental JSON: " + rentalMapper.getDTOAsJSON(
                rentalMapper.getDTOFromRentals(rentals)
        ));

        // Then
        assertNotNull(rentals);
        assertEquals(2, rentals.size());
        assertEquals("bel appart", rentals.get(0).getName());
        assertEquals("modern studio", rentals.get(1).getName());

        // Convert to JSON
        //ObjectMapper objectMapper = new ObjectMapper();
        //objectMapper.registerModule(new JavaTimeModule()); // Register the module for Java 8 date/time types

        String rentalsJSON = rentalMapper.getDTOAsJSON(
                rentalMapper.getDTOFromRentals(
                        service.getAllRentals()
                )
        );

        assertNotNull(rentalsJSON);
        String expectedJSON = "[{\"id\":1,\"name\":\"bel appart\",\"surface\":55.0,\"price\":950.0,\"picture\":\"belle facade\",\"description\":\"T2 spacieux et lumineux\",\"createdAt\":\""
                + fakeDateProvider.now()+ "\",\"updatedAt\":\"" + fakeDateProvider.now() +",1"+ "\"}," +
                "{\"id\":2,\"name\":\"modern studio\",\"surface\":40.0,\"price\":750.0,\"picture\":\"vue sur parc\",\"description\":\"Studio moderne et bien agencé\",\"createdAt\":\""
                + fakeDateProvider.now() + "\",\"updatedAt\":\"" + fakeDateProvider.now()  +",1"+ "\"}]";

        assertEquals(expectedJSON, rentalsJSON);
    }
}
