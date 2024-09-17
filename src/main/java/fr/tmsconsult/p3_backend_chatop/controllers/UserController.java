package fr.tmsconsult.p3_backend_chatop.controllers;
import fr.tmsconsult.p3_backend_chatop.services.interfaces.IJwtService;
import org.springframework.security.core.AuthenticationException;

import fr.tmsconsult.p3_backend_chatop.dtos.Responses.JwtResponse;
import fr.tmsconsult.p3_backend_chatop.dtos.Responses.UserResponse;
import fr.tmsconsult.p3_backend_chatop.dtos.requests.LoginRequest;
import fr.tmsconsult.p3_backend_chatop.dtos.requests.RegisterRequest;
import fr.tmsconsult.p3_backend_chatop.entities.User;
import fr.tmsconsult.p3_backend_chatop.exceptions.AuthenticationFailedException;
import fr.tmsconsult.p3_backend_chatop.exceptions.EmailAlreadyExistsException;
import fr.tmsconsult.p3_backend_chatop.mappers.UserMapper;
import fr.tmsconsult.p3_backend_chatop.services.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    private final UserService service;
    private final IJwtService jwtService;
    private final UserMapper userMapper = UserMapper.INSTANCE;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @Operation(summary = "register a user")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegisterRequest.class))}),
            @ApiResponse(responseCode = "400", description = "cannot register this user!",
                    content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {

            User userRequested = new User(0,
                    registerRequest.getEmail(),
                    registerRequest.getPassword(),
                    registerRequest.getName(),
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );
            Optional<User> existingUser = service.fetchUserByEmail(registerRequest.getEmail());
            if (existingUser.isPresent()) {
                throw new EmailAlreadyExistsException("Email already exists: " + registerRequest.getEmail());
            }
            User userCreated =
                    service.register(userRequested);
            return ResponseEntity.ok(new JwtResponse(service.generateToken(userCreated.getEmail())));
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyMap());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyMap());
        }

    }

    @Operation(summary = "login a user")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User login",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginRequest.class))}),
            @ApiResponse(responseCode = "401", description = "bad credentials!",
                    content = @Content)
    })
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            logger.info("Login request: {}", loginRequest);
            String token = service.verifyEmailAndPassword(new User(
                    0,
                    loginRequest.getLogin(),
                    loginRequest.getPassword(),
                    "",
                    null,
                    null));

            return ResponseEntity.ok(new JwtResponse(token));
        } catch (AuthenticationException e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new AuthenticationFailedException("Authentication failed"));
        }
    }

    @GetMapping("/me")
    @Operation(summary = "Get the currently authenticated user", security = @SecurityRequirement(name = "Bearer Authentication"))

    public UserResponse loadConnectedUser(HttpServletRequest request) {
        String token = jwtService.extractTokenFromRequest(request);
        Optional<User> user = service.fetchUserByEmail(jwtService.extractEmail(token));
        if (!user.isPresent()) {
            return new UserResponse();
        } else {
            return userMapper.userToUserDTO(user.get());
        }

    }
}
