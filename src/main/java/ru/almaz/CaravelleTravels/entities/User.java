package ru.almaz.CaravelleTravels.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    public User() {
    }

    public User(Long chatId) {
        this.chatId = chatId;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id", nullable = false, unique = true)
    private Long chatId;

    @Column(name = "telegram_user_name")
    private String telegramUserName;

    @Column(name = "permissions", nullable = false)
    private boolean permissions = false;

    @Column(name = "booking_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private BookingState bookingState = BookingState.NONE;

    @Column(name = "processing_booking_id")
    private Long processingBooking;

    @OneToMany(mappedBy = "user")
    private List<Booking> bookings;

    public void setTelegramUserName(String userName) {
        telegramUserName = "@" + userName;
    }
}
