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
    private Message message;

    @BeforeEach
    void setUp() {
        fakeMessageRepository = new FakeMessageRepository();
        messageService = new MessageService(fakeMessageRepository);

        // Initialize a common Message object for all tests
        message = new Message();
        message.setMessage("Hello, I am interested in this rental.");
        message.setRentalId(1);
        message.setUserId(1);
        message.setCreatedAt(LocalDateTime.now());
        message.setUpdatedAt(LocalDateTime.now());

        // Add the message
        messageService.add(message);
    }

    @Test
    void canSaveMessage() {
        // Assert the message was saved
        assertNotNull(fakeMessageRepository.findById(message.getId()));
    }

    @Test
    void canCountMessages() {
        // Assert that the message repository has one entry
        assertEquals(1, fakeMessageRepository.count());
    }

    @Test
    void canRetrieveSavedMessage() {
        // Retrieve and assert the saved message is not null
        Message savedMessage = fakeMessageRepository.findById(message.getId()).orElse(null);
        assertNotNull(savedMessage);
    }

    @Test
    void canVerifyMessageContent() {
        // Assert that the message content matches
        Message savedMessage = fakeMessageRepository.findById(message.getId()).orElse(null);
        assertEquals("Hello, I am interested in this rental.", savedMessage.getMessage());
    }

    @Test
    void canVerifyRentalId() {
        // Assert that the rental ID matches
        Message savedMessage = fakeMessageRepository.findById(message.getId()).orElse(null);
        assertEquals(1, savedMessage.getRentalId());
    }

    @Test
    void canVerifyUserId() {
        // Assert that the user ID matches
        Message savedMessage = fakeMessageRepository.findById(message.getId()).orElse(null);
        assertEquals(1, savedMessage.getUserId());
    }
}
