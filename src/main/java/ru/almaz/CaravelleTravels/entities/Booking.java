package ru.almaz.CaravelleTravels.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.Lazy;

import java.util.Date;

@Data
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(name = "from_place")
    private String fromPlace;

    @Column(name = "to_place")
    private String toPlace;

    @Column(name = "date")
    private String date;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "passenger_name")
    private String passengerName;

    @Column(name = "passengers_count")
    private Integer passengersCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status", nullable = false)
    private BookingStatus bookingStatus = BookingStatus.CREATING;
}
