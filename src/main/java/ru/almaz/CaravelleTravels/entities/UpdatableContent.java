package ru.almaz.CaravelleTravels.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "updatable_contents")
public class UpdatableContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lexic_id", unique = true, nullable = false)
    private String lexicId;

    @Column(name = "text_content", nullable = false)
    private String textContent;
}
