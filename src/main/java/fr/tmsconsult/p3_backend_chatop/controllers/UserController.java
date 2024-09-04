package fr.tmsconsult.p3_backend_chatop.controllers;

import fr.tmsconsult.p3_backend_chatop.config.JwtUtil;
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

    @PostMapping(value = "/login" , produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> login(@RequestBody User user) {


        return ResponseEntity.ok(service.verify(user));
    }
    @GetMapping("/me")
    public UserDTO loadConnectedUser(HttpServletRequest request) {
        String token =jwtUtil.extractTokenFromRequest(request);
        return service.fetchUserDTOByToken(token);
    }
}
