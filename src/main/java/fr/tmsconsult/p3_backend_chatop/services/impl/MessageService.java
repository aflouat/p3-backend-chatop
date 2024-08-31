package fr.tmsconsult.p3_backend_chatop.services.impl;

import fr.tmsconsult.p3_backend_chatop.entities.Message;
import fr.tmsconsult.p3_backend_chatop.repositories.MessageRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
    public void add(Message message) {

        messageRepository.save(message);
    }

}
