package fr.tmsconsult.p3_backend_chatop.mappers;

import fr.tmsconsult.p3_backend_chatop.dtos.MessageDTO;
import fr.tmsconsult.p3_backend_chatop.entities.Message;
import fr.tmsconsult.p3_backend_chatop.models.DeterministicDateProvider;

import java.time.LocalDateTime;

public class MessageMapper {
    public Message getOneFromRequestToCreate(MessageDTO messageDTO){
        return new Message(0,
                messageDTO.getMessage(),
                messageDTO.getRentalId(),
                messageDTO.getUserId(),
                LocalDateTime.now(),
                LocalDateTime.now());
    }

}
