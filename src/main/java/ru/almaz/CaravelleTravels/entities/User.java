package ru.almaz.CaravelleTravels.entities;

import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    public User(Long chatId) {
        this.chatId = chatId;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id", nullable = false, unique = true)
    private Long chatId;

    @Column(name = "permissions", nullable = false)
    private boolean permissions = false;

    @Column(name = "booking_state", nullable = false)
    private String bookingState = "noob";

    @Column(name = "booking_id")
    private Long bookingId;


}
