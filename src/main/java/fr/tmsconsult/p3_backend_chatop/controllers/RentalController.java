package fr.tmsconsult.p3_backend_chatop.controllers;

import fr.tmsconsult.p3_backend_chatop.dtos.RentalDTO;
import fr.tmsconsult.p3_backend_chatop.entities.Rental;
import fr.tmsconsult.p3_backend_chatop.entities.User;
import fr.tmsconsult.p3_backend_chatop.mappers.RentalMapper;
import fr.tmsconsult.p3_backend_chatop.security.model.JwtResponse;
import fr.tmsconsult.p3_backend_chatop.security.service.JWTService;
import fr.tmsconsult.p3_backend_chatop.services.RentalService;
import fr.tmsconsult.p3_backend_chatop.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api")

public class RentalController {
    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);
    public static final String RENTAL_CREATED = "Rental created !";
    public static final String CANNOT_SUBMIT_RENTAL_PLEASE_CHECK_AND_TRY_AGAIN = "cannot submit rental, please check and try again";
    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserService userService;
    @Autowired
    private RentalService rentalService;


    private RentalMapper rentalMapper = new RentalMapper();
    @Operation(summary = "get all rentals for a connected user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieve rentals",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Cannot retrieve rentals!",
                    content = @Content)
    })
    @GetMapping(value = "/rentals", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllRentals(@RequestHeader("Authorization") String token) {
        logger.info(" request header rentals: {}", token);
        try {
            jwtService.checkJWTValidity(token);

            List<Rental> rentals = rentalService.getAllRentals();

            List<RentalDTO> rentalsDTO = rentalMapper.getDTOFromRentals(rentals);
            RentalMapper rentalMapper = new RentalMapper();
            return ResponseEntity.ok(rentalMapper.getDTOAsJSON(rentalsDTO));
        } catch (Exception e) {

            return ResponseEntity.status(400).body(e.getMessage());
        }
    }




    @Operation(summary = "get one rental by id for a connected user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieve the searched rental",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Rental not found!",
                    content = @Content)
    })
    @GetMapping(value = "/rentals/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findRentalById(@RequestHeader("Authorization") String token, @PathVariable Integer id) {
        try {
            User uRetrieved = userService.findUserByEmail(
                    jwtService.findEmailByToken(token));

            RentalDTO foundedRental = rentalMapper.getDTOFromRental(
                    rentalService.findRentalById(id)
            );
            String rentalDTOJSON = rentalMapper.getDTOAsJSON(foundedRental);

            return ResponseEntity.ok(rentalDTOJSON);
        } catch (Exception e) {

            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
        @Operation(summary = "submit a rental")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = RENTAL_CREATED,
                        content = { @Content(mediaType = "application/json",
                                schema = @Schema(implementation = JwtResponse.class)) }),
                @ApiResponse(responseCode = "400", description = CANNOT_SUBMIT_RENTAL_PLEASE_CHECK_AND_TRY_AGAIN,
                        content = @Content)
        })
        @PostMapping(value = "/rentals", consumes = {"multipart/form-data"})
        public ResponseEntity<?> addRental(@RequestHeader("Authorization") String token,
                                           @RequestParam("name") String name,
                                           @RequestParam("surface") float surface,
                                           @RequestParam("price") float price,
                                           @RequestParam("picture") MultipartFile  picture,
                                           @RequestParam("description") String description
                                            ) {
            try {

                rentalService.addRental(
                        rentalMapper.getOneFromRequest(
                                0,
                                name,
                                surface,
                                price,
                                picture,
                                description
                        )
                );

                return ResponseEntity.ok(RENTAL_CREATED);
            } catch (Exception e) {
                return ResponseEntity.status(400).body(CANNOT_SUBMIT_RENTAL_PLEASE_CHECK_AND_TRY_AGAIN);
            }
        }

    @Operation(summary = "update a rental")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rental updated!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "cannot update the rental requested, please check and try again",
                    content = @Content)
    })
    @PutMapping (value = "/rentals/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<?> updateRental(@RequestHeader("Authorization") String token,
                                          @PathVariable Integer id,
                                       @RequestParam("name") String name,
                                       @RequestParam("surface") float surface,
                                       @RequestParam("price") float price,
                                       @RequestParam("picture") MultipartFile  picture,
                                       @RequestParam("description") String description
    ) {
        try {

            rentalService.updateRental(
                    rentalMapper.getOneFromRequest(
                            id,
                            name,
                            surface,
                            price,
                            picture,
                            description
                    )
            );

            return ResponseEntity.ok(RENTAL_CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(CANNOT_SUBMIT_RENTAL_PLEASE_CHECK_AND_TRY_AGAIN);
        }
    }

}




