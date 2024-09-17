package fr.tmsconsult.p3_backend_chatop.controllers;

import fr.tmsconsult.p3_backend_chatop.dtos.Responses.MessageSentDTO;
import fr.tmsconsult.p3_backend_chatop.dtos.requests.MessageRequest;
import fr.tmsconsult.p3_backend_chatop.entities.Message;
import fr.tmsconsult.p3_backend_chatop.mappers.MessageMapper;
import fr.tmsconsult.p3_backend_chatop.dtos.Responses.JwtResponse;
import fr.tmsconsult.p3_backend_chatop.services.impl.MessageServiceImpl;
import fr.tmsconsult.p3_backend_chatop.services.impl.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MessageController {
    public static final String CANNOT_SEND_THE_MESSAGE_PLEASE_CHECK_AND_RETRY_AGAIN = "Cannot send the message! please check and retry again!";
    public static final String MESSAGE_SENT = "Message send with success";
    private final MessageServiceImpl messageServiceImpl;
    //TODO utiliser l'interface pour le MyUserDetailsServiceImpl
    private final UserDetailsServiceImpl myUserDetailsServiceImpl;
    private final MessageMapper messageMapper = MessageMapper.INSTANCE;

    @Operation(summary = "Send a message regarding the rental")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message sent",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtResponse.class)) }),
            @ApiResponse(responseCode = "400", description = CANNOT_SEND_THE_MESSAGE_PLEASE_CHECK_AND_RETRY_AGAIN,
                    content = @Content)
    })
    @PostMapping(value = "/messages", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sentMessage(@RequestBody MessageRequest messageRequest
    ) {
        try {
            Message message = messageMapper.messageDTOToMessage(messageRequest);
            message.setCreatedAt(LocalDateTime.now());
            message.setUpdatedAt(LocalDateTime.now());
            messageServiceImpl.add(message);

            return ResponseEntity.ok(new MessageSentDTO(MESSAGE_SENT));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(CANNOT_SEND_THE_MESSAGE_PLEASE_CHECK_AND_RETRY_AGAIN+" "+e.getMessage());
        }
    }

}
