package fr.tmsconsult.p3_backend_chatop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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

    private String description;

    private LocalDate createdAt;
    private LocalDate updatedAt;
}
