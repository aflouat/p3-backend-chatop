package fr.tmsconsult.p3_backend_chatop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "RENTALS")
@Data @NoArgsConstructor
@AllArgsConstructor
@Getter
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private float surface;


    private float price;
    private String picture;
    @Column(length = 1000)

    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int ownerId;
}
