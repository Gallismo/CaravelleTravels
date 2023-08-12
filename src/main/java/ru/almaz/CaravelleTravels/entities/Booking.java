package ru.almaz.CaravelleTravels.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long user_id;

    @Column(name = "from_place", nullable = false)
    private String fromPlace;

    @Column(name = "to_place", nullable = false)
    private String toPlace;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "passenger_name", nullable = false)
    private String passengerName;
}
