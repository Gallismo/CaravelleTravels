package ru.almaz.CaravelleTravels.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Entity
@Table(name = "bookings", indexes = @Index(columnList = "date"))
@Slf4j
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(name = "from_place")
    private String fromPlace;

    @Column(name = "to_place")
    private String toPlace;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date date;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "passenger_name")
    private String passengerName;

    @Column(name = "passengers_count")
    private Integer passengersCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status", nullable = false)
    private BookingStatus bookingStatus = BookingStatus.CREATING;

    public void setDate(String date) {
        if (date.matches(BookingState.DATE.getRegex())) {
            try {
                this.date = BookingState.dateFormatter().parse(date);
            } catch (ParseException e) {
                log.error(e.getMessage());
            }
        }
    }

    public String getDate() {
        return BookingState.dateFormatter().format(this.date);
    }

    public void setPhoneNumber(String value) {
        if (value.matches("\\+?\\d{11}")) {
            int length = value.length();
            value = value.substring(length - 10, length);
        } else if (value.matches("\\+?\\d \\d{3} \\d{3} \\d{2} \\d{2}")) {
            value = String.join("", value.split(" "));
            int length = value.length();
            value = value.substring(length - 10, length);
        } else if (value.matches("\\+?\\d-\\d{3}-\\d{3}-\\d{2}-\\d{2}")) {
            value = String.join("", value.split("-"));
            int length = value.length();
            value = value.substring(length - 10, length);
        }
        phoneNumber = "+7" + value;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", fromPlace='" + fromPlace + '\'' +
                ", toPlace='" + toPlace + '\'' +
                ", date=" + date +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", passengerName='" + passengerName + '\'' +
                ", passengersCount=" + passengersCount +
                ", bookingStatus=" + bookingStatus +
                '}';
    }

    public String toMessage() {
        return "№" + getId() + "\nДата: " + getDate() + "\nПосадка: " + fromPlace +
                "\nВысадка: " + toPlace + "\nКоличество мест: " + passengersCount +
                "\nНомер телефона: " + phoneNumber + "\nИмя: " + passengerName +
                "\nTelegram: " + user.getTelegramUserName();
    }
}
