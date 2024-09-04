package fr.tmsconsult.p3_backend_chatop.mappers;

import fr.tmsconsult.p3_backend_chatop.dtos.requests.MessageDTO;
import fr.tmsconsult.p3_backend_chatop.dtos.requests.RentalDTO;
import fr.tmsconsult.p3_backend_chatop.entities.Message;
import fr.tmsconsult.p3_backend_chatop.entities.Rental;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
@Mapper
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);
    @Mapping(target = "id", ignore = true)
    Message messageDTOToMessage(MessageDTO messageDTO);



}
