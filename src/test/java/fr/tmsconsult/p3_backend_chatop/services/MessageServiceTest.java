package fr.tmsconsult.p3_backend_chatop.services;

import fr.tmsconsult.p3_backend_chatop.entities.Message;
import fr.tmsconsult.p3_backend_chatop.repositories.FakeMessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class MessageServiceTest {

    private FakeMessageRepository fakeMessageRepository;
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        fakeMessageRepository = new FakeMessageRepository();
        messageService = new MessageService(fakeMessageRepository);
    }

    @Test
    void canCreateMessage() {
        // Given
        Message message = new Message();
        message.setMessage("Hello, I am interested in this rental.");
        message.setRentalId(1);
        message.setUserId(1);
        message.setCreatedAt(LocalDateTime.now());
        message.setUpdatedAt(LocalDateTime.now());

        // When
        messageService.add(message);

        // Then
        assertNotNull(fakeMessageRepository.findById(message.getId()));
        assertEquals(1, fakeMessageRepository.count());
        Message savedMessage = fakeMessageRepository.findById(message.getId()).orElse(null);
        assertNotNull(savedMessage);
        assertEquals("Hello, I am interested in this rental.", savedMessage.getMessage());
        assertEquals(1, savedMessage.getRentalId());
        assertEquals(1, savedMessage.getUserId());
    }
}