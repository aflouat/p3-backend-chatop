package fr.tmsconsult.p3_backend_chatop.services.impl;

import fr.tmsconsult.p3_backend_chatop.entities.Message;
import fr.tmsconsult.p3_backend_chatop.repositories.MessageRepository;
import fr.tmsconsult.p3_backend_chatop.services.interfaces.IMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements IMessageService {
    private final MessageRepository messageRepository;



    public void add(Message message) {

        messageRepository.save(message);
    }

}
