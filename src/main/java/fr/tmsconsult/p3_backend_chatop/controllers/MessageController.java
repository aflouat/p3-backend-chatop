package fr.tmsconsult.p3_backend_chatop.controllers;

import fr.tmsconsult.p3_backend_chatop.dtos.requests.MessageDTO;
import fr.tmsconsult.p3_backend_chatop.mappers.MessageMapper;
import fr.tmsconsult.p3_backend_chatop.dtos.Responses.JwtResponse;
import fr.tmsconsult.p3_backend_chatop.services.impl.MessageServiceImpl;
import fr.tmsconsult.p3_backend_chatop.services.impl.MyUserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MessageController {
    public static final String CANNOT_SEND_THE_MESSAGE_PLEASE_CHECK_AND_RETRY_AGAIN = "Cannot send the message! please check and retry again!";
    private final MessageServiceImpl messageServiceImpl;

    private final MyUserDetailsServiceImpl myUserDetailsServiceImpl;

    public MessageController(MessageServiceImpl messageServiceImpl, MyUserDetailsServiceImpl myUserDetailsServiceImpl) {
        this.messageServiceImpl = messageServiceImpl;
        this.myUserDetailsServiceImpl = myUserDetailsServiceImpl;
    }

    private MessageMapper messageMapper = new MessageMapper();

    @Operation(summary = "Send a message regarding the rental")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message sent",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtResponse.class)) }),
            @ApiResponse(responseCode = "400", description = CANNOT_SEND_THE_MESSAGE_PLEASE_CHECK_AND_RETRY_AGAIN,
                    content = @Content)
    })
    @PostMapping(value = "/messages", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sentMessage(@RequestHeader("Authorization") String token,
                                         @RequestBody MessageDTO messageDTO
    ) {
        try {
            System.out.println(token + " " + messageDTO);


            messageServiceImpl.add(
                    messageMapper.getOneFromRequestToCreate(
                            messageDTO
                    )
            );

            return ResponseEntity.ok("message sent");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(CANNOT_SEND_THE_MESSAGE_PLEASE_CHECK_AND_RETRY_AGAIN+" "+e.getMessage());
        }
    }

}
