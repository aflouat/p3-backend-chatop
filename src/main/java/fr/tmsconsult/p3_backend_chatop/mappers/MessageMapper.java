package fr.tmsconsult.p3_backend_chatop.mappers;

import fr.tmsconsult.p3_backend_chatop.dtos.requests.MessageRequest;
import fr.tmsconsult.p3_backend_chatop.entities.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);
    @Mapping(target = "id", ignore = true)
    Message messageDTOToMessage(MessageRequest messageRequest);



}
