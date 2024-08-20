package fr.tmsconsult.p3_backend_chatop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.tmsconsult.p3_backend_chatop.dtos.CurrentUserDTO;
import fr.tmsconsult.p3_backend_chatop.dtos.RentalDTO;
import fr.tmsconsult.p3_backend_chatop.entities.Rental;
import fr.tmsconsult.p3_backend_chatop.entities.User;
import fr.tmsconsult.p3_backend_chatop.security.model.JwtResponse;
import fr.tmsconsult.p3_backend_chatop.security.service.JWTService;
import fr.tmsconsult.p3_backend_chatop.services.RentalService;
import fr.tmsconsult.p3_backend_chatop.services.UserService;
import io.jsonwebtoken.Claims;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")

public class RentalController {
    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);
    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserService userService;
    @Autowired
    private RentalService rentalService;
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
            User uRetrieved = userService.findUserByEmail(
                    jwtService.findEmailByToken(token));

            List<RentalDTO> rentals = rentalService.getAllRentals();
            ObjectMapper objectMapper = new ObjectMapper();

            // Convert the CurrentUserDTO to JSON
            String rentalsDTOJSON = objectMapper.writeValueAsString(rentals);

            return ResponseEntity.ok(rentalsDTOJSON);
        } catch (Exception e) {

            return ResponseEntity.status(404).body(e.getMessage());
        }
    }


}
