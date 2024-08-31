package fr.tmsconsult.p3_backend_chatop.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;




}
