package fr.tmsconsult.p3_backend_chatop.mappers;

import fr.tmsconsult.p3_backend_chatop.dtos.Responses.UserResponse;
import fr.tmsconsult.p3_backend_chatop.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    // Assuming createdAt and updatedAt are available in User entity or will be manually set in DTO
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    UserResponse userToUserDTO(User user);

    // Reverse mapping
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(target = "password", ignore = true) // Password should typically not be included in DTOs for security
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy-MM-dd HH:mm:ss")

    User userDTOToUser(UserResponse userResponse);
    default String map(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATE_FORMATTER) : null;
    }

    default LocalDateTime map(String dateTime) {
        return dateTime != null ? LocalDateTime.parse(dateTime, DATE_FORMATTER) : null;
    }
}

