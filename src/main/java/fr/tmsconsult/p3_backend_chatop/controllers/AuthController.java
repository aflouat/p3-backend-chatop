package fr.tmsconsult.p3_backend_chatop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.tmsconsult.p3_backend_chatop.dtos.CurrentUserDTO;
import fr.tmsconsult.p3_backend_chatop.entities.User;
import fr.tmsconsult.p3_backend_chatop.models.DeterministicDateProvider;
import fr.tmsconsult.p3_backend_chatop.security.model.JwtResponse;
import fr.tmsconsult.p3_backend_chatop.security.model.LoginRequest;

import fr.tmsconsult.p3_backend_chatop.security.model.RegisterRequest;
import fr.tmsconsult.p3_backend_chatop.security.service.JWTService;
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
import org.springframework.web.bind.annotation.*;
import fr.tmsconsult.p3_backend_chatop.services.UserService;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    public static final String CANNOT_RETRIEVE_USER_PLEASE_TRY_AGAIN  = "cannot retrieve user, please try again!";
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    public static final String CANNOT_REGISTER_USER_PLEASE_CHECK_DATA_AND_TRY_AGAIN = "cannot register user, please check data and try again!";


    @Autowired
    private UserService userService;


    @Autowired
    private JWTService jwtService;

    @Operation(summary = "Authenticate user and return a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully authenticated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "Invalid email or password",
                    content = @Content)
    })
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = userService.login(loginRequest.getLogin(), loginRequest.getPassword());
            return ResponseEntity.ok(new JwtResponse(token));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

    @Operation(summary = "register a new user and return success")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registrated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "cannot register user, please try again",
                    content = @Content)
    })
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        logger.info("Register request: {}", registerRequest+" - Email :"+registerRequest.getLogin());
        try {
            User uSaved = userService.register(registerRequest.getName(),registerRequest.getLogin(),
                    registerRequest.getPassword(), LocalDateTime.now(),LocalDateTime.now() );
            return ResponseEntity.ok(new JwtResponse(uSaved.toString()));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(CANNOT_REGISTER_USER_PLEASE_CHECK_DATA_AND_TRY_AGAIN+" _ "+e.getMessage());
        }
    }

    @Operation(summary = "show informations about the connected user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registrated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "cannot register user, please try again",
                    content = @Content)
    })
    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String token) {
        logger.info(" request header auth: {}", token);
        try {
            String email = jwtService.findEmailByToken(token);

            User uRetrieved = userService.findUserByEmail(email);
            CurrentUserDTO currentUserDTO = new CurrentUserDTO(uRetrieved.getId(),uRetrieved.getName(),uRetrieved.getEmail(),
                    uRetrieved.getCreatedAt().toString(),uRetrieved.getUpdatedAt().toString());
            ObjectMapper objectMapper = new ObjectMapper();

            // Convert the CurrentUserDTO to JSON
            String CurrentUserDTOJSON = objectMapper.writeValueAsString(currentUserDTO);

            return ResponseEntity.ok(CurrentUserDTOJSON.toString());
        } catch (Exception e) {

            return ResponseEntity.status(404).body(e.getMessage() );
        }
    }
}
