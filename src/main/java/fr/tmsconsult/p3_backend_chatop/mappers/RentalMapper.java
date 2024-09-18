package fr.tmsconsult.p3_backend_chatop.mappers;


import fr.tmsconsult.p3_backend_chatop.dtos.requests.RentalDTO;
import fr.tmsconsult.p3_backend_chatop.dtos.requests.RentalRequest;
import fr.tmsconsult.p3_backend_chatop.entities.Rental;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RentalMapper {
    RentalMapper INSTANCE = Mappers.getMapper(RentalMapper.class);

    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy/MM/dd")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy/MM/dd")
    RentalDTO rentalToRentalDTO(Rental rental);

    @Mapping(target = "picture", ignore = true) // Since you're handling file upload separately
    Rental rentalDTORequestParamToRental(RentalRequest rentalRequest);


}
