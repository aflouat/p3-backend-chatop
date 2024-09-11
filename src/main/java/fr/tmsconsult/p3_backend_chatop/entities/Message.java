package fr.tmsconsult.p3_backend_chatop.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "MESSAGES")
@Data

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String message;

    private int rentalId;

    private int userId;




}
