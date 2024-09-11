package fr.tmsconsult.p3_backend_chatop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.tmsconsult.p3_backend_chatop.config.JwtUtil;
import fr.tmsconsult.p3_backend_chatop.config.WebConfig;
import fr.tmsconsult.p3_backend_chatop.dtos.Responses.AllRentalsDTO;
import fr.tmsconsult.p3_backend_chatop.dtos.Responses.RentalCreatedDTO;
import fr.tmsconsult.p3_backend_chatop.dtos.requests.RentalDTO;
import fr.tmsconsult.p3_backend_chatop.dtos.requests.RentalDTORequestParam;
import fr.tmsconsult.p3_backend_chatop.entities.Rental;
import fr.tmsconsult.p3_backend_chatop.mappers.RentalMapper;
import fr.tmsconsult.p3_backend_chatop.dtos.Responses.JwtResponse;
import fr.tmsconsult.p3_backend_chatop.services.impl.RentalService;
import fr.tmsconsult.p3_backend_chatop.services.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RentalController {
    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);
    public static final String RENTAL_CREATED = "Rental created !";
    public static final String CANNOT_SUBMIT_RENTAL_PLEASE_CHECK_AND_TRY_AGAIN = "cannot submit rental, please check and try again";
    private static final String RENTAL_UPDATED_SUCCESSFULLY = "Rental updated successfully !"; ;

    private final RentalService rentalService;
    private final WebConfig webConfig;
    private final UserService userService;


    private final RentalMapper rentalMapper = RentalMapper.INSTANCE; // Accessing the mapper instance
    private final JwtUtil jwtUtil;


    @Operation(summary = "get all rentals for a connected user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieve rentals",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Cannot retrieve rentals!",
                    content = @Content)
    })
    @GetMapping(value = "/rentals", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllRentals(HttpServletRequest request) {
        logger.info(" request  rentals: {}", request);
        try {
            List<Rental> rentals = rentalService.getAllRentals();
            List<RentalDTO> rentalDTOS = new ArrayList<>();
            for (Rental rental : rentals) {
                rentalDTOS.add(rentalMapper.rentalToRentalDTO(rental));
            }

            String jsonResponse = new ObjectMapper().
                    writeValueAsString(
                            new AllRentalsDTO(rentalDTOS));
            return ResponseEntity.ok(jsonResponse);
        } catch (Exception e) {
            logger.error(e.getMessage());
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


            RentalDTO foundedRental = rentalMapper.rentalToRentalDTO(
                    rentalService.findRentalById(id)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            return ResponseEntity.ok(objectMapper.writeValueAsString(foundedRental));
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
        public ResponseEntity<?> addRental(HttpServletRequest request,
                                           @ModelAttribute RentalDTORequestParam rentalDTORequestParam
                                            ) {
            try {
                String uploadDir = System.getProperty("user.dir") + "/"+webConfig.getUploadDir();
                File uploadFile = new File(uploadDir + rentalDTORequestParam.getPicture().getOriginalFilename());
                rentalDTORequestParam.getPicture().transferTo(uploadFile);
                String fileUrl = webConfig.getProtocol()+ "://"+
                        webConfig.getHostname()+":"+webConfig.getPort()+"/"+
                        webConfig.getUploadDir() +
                        rentalDTORequestParam.getPicture().getOriginalFilename();
                String token =jwtUtil.extractTokenFromRequest(request);

                rentalService.addRental(
                        new Rental(0,
                                rentalDTORequestParam.getName(),
                                rentalDTORequestParam.getSurface(),
                                rentalDTORequestParam.getPrice(),
                                fileUrl,
                                rentalDTORequestParam.getDescription(),
                                LocalDate.now(),
                                LocalDate.now(),
                                userService.fetchUserByToken(token).getId()
                        )
                );

                return ResponseEntity.ok(new RentalCreatedDTO(RENTAL_CREATED));

            } catch (Exception e) {
                logger.error(e.getMessage());
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
                                       @ModelAttribute RentalDTORequestParam rentalDTORequestParam
    ) {
        try {
            rentalDTORequestParam.setId(id);
            rentalService.updateRental(
                    rentalMapper.rentalDTORequestParamToRental(rentalDTORequestParam)
            );

            return ResponseEntity.ok(new RentalCreatedDTO(RENTAL_UPDATED_SUCCESSFULLY));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(400).body(CANNOT_SUBMIT_RENTAL_PLEASE_CHECK_AND_TRY_AGAIN);
        }
    }

}




