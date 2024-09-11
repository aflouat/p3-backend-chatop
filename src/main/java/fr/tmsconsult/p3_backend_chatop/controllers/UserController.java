package fr.tmsconsult.p3_backend_chatop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.tmsconsult.p3_backend_chatop.config.JwtUtil;
import fr.tmsconsult.p3_backend_chatop.dtos.Responses.AllRentalsDTO;
import fr.tmsconsult.p3_backend_chatop.dtos.Responses.JwtResponse;
import fr.tmsconsult.p3_backend_chatop.dtos.Responses.UserDTO;
import fr.tmsconsult.p3_backend_chatop.entities.User;
import fr.tmsconsult.p3_backend_chatop.services.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    private final UserService service;
    private final JwtUtil jwtUtil;
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return service.register(user);

    }

    @PostMapping(value = "/login" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();
        String token = service.verify(user);

        return ResponseEntity.ok(new JwtResponse(token));
    }
    @GetMapping("/me")
    public UserDTO loadConnectedUser(HttpServletRequest request) {
        String token =jwtUtil.extractTokenFromRequest(request);
        return service.fetchUserDTOByToken(token);
    }
}
