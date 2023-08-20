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

    @Column(name = "lexic_id", unique = true, nullable = false, columnDefinition = "TEXT")
    private String lexicId;

    @Column(name = "text_content", nullable = false, columnDefinition = "TEXT")
    private String textContent;

    public String toMessage() {
        return lexicId + "\n\n" + textContent;
    }
}
