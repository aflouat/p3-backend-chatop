package fr.tmsconsult.p3_backend_chatop.services;

import fr.tmsconsult.p3_backend_chatop.entities.User;
import fr.tmsconsult.p3_backend_chatop.models.DeterministicDateProvider;
import fr.tmsconsult.p3_backend_chatop.repositories.FakeUserRepository;
import fr.tmsconsult.p3_backend_chatop.security.model.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    public static final String MAIL_NOT_FOUND = "not_found@test.com";
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    private UserService userService;
    private FakeUserRepository fakeUserRepository;

    private Token token;
    private static final String secretKey = "REdBd2NtVmpTN0tyczl1YmE3WFY0NC9adXNLUHZDTHIzN2dDMFkyUkdYODFxVFp4NzA2SU5lbFI2ODdQc3YyKw==";
    private final DeterministicDateProvider fakeDateProvider = new DeterministicDateProvider();

    @BeforeEach
    void setUp() {
        fakeDateProvider.currentDate = LocalDate.of(2020, 2, 4);
        fakeUserRepository = new FakeUserRepository();
        token = new Token();
        token.setSecret(secretKey);
        token.init();
        userService = new UserService(fakeUserRepository, token);

        fakeUserRepository.clear();
    }

    @Test
    void canGenerateTokenWithValidCredentials() throws Exception {
        // Given
        String email = "test@test.com";
        String password = "test!31";
        fakeUserRepository.save(new User(1, email, " ", passwordEncoder.encode(password),
                fakeDateProvider.now().atStartOfDay(), fakeDateProvider.now().atStartOfDay()));

        // When
        String token = userService.login(email, password);
        System.out.println(token);

        // Then
        assertNotNull(token);
    }

    @Test
    void shouldFailLoginWithInvalidCredentials() {
        // Given
        String email = "wrong@test.com";
        String password = "wrongpassword";

        // When & Then
        assertThrows(Exception.class, () -> {
            userService.login(email, password);
        });
    }

    @Test
    void canVerifyPasswordWithBCryptEncoder() throws Exception {
        // Given
        String email = "test@test.com";
        String rawPassword = "test!31";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(1, email, " ", encodedPassword,
                fakeDateProvider.now().atStartOfDay(), fakeDateProvider.now().atStartOfDay());
        fakeUserRepository.save(user);

        // When
        boolean passwordMatches = passwordEncoder.matches(rawPassword, user.getPassword());

        // Then
        assertTrue(passwordMatches);
    }

    @Test
    void shouldThrowExceptionForUnknownUser() {
        // Given
        String email = "unknown@test.com";
        String password = "password";

        // When & Then
        assertThrows(Exception.class, () -> {
            userService.login(email, password);
        });
    }

    @Test
    void shouldThrowsWhenLoginNotFound() throws Exception {
        // Given
        String email = "test@test.com";
        String password = "test!31";
        User user = new User(1, email, " ", passwordEncoder.encode(password),
                fakeDateProvider.now().atStartOfDay(), fakeDateProvider.now().atStartOfDay());
        fakeUserRepository.save(user);

        // When
       // String token = userService.login(MAIL_NOT_FOUND, password);
        //System.out.println(token);

        // Then
        assertThrows(Exception.class, () -> {
            userService.login(MAIL_NOT_FOUND, password);
        });
    }

    @Test
    void canVerifyTokenBelongsToCorrectUser() throws Exception {
        // Given
        String email = "test@test.com";
        String password = "test!31";
        User user = new User(1, email, " ", passwordEncoder.encode(password),
                fakeDateProvider.now().atStartOfDay(), fakeDateProvider.now().atStartOfDay());
        fakeUserRepository.save(user);

        // When
        String generatedToken = userService.login(email, password);
        String jwt = generatedToken.replace("Bearer ", "");

        // Parse the token to get the claims
        Claims claims = Jwts.parser()
                .setSigningKey(token.getKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        // Then
        String tokenEmail = claims.get("email", String.class);
        assertEquals(email, tokenEmail);
    }

    @Test
    void shouldGenerateTokenValidForAtLeast15Minutes() throws Exception {
        // Given
        String email = "test@test.com";
        String password = "test!31";
        User user = new User(1, email, " ", passwordEncoder.encode(password),
                fakeDateProvider.now().atStartOfDay(), fakeDateProvider.now().atStartOfDay());
        fakeUserRepository.save(user);

        // When
        String generatedToken = userService.login(email, password);
        String jwt = generatedToken.replace("Bearer ", "");

        // Parse the token to get the claims
        Claims claims = Jwts.parser()
                .setSigningKey(token.getKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        // Then
        Date issuedAt = claims.getIssuedAt();
        Date expiration = claims.getExpiration();
        long durationInMillis = expiration.getTime() - issuedAt.getTime();
        long durationInMinutes = durationInMillis / (1000 * 60);

        assertTrue(durationInMinutes >= 15);
    }
}
