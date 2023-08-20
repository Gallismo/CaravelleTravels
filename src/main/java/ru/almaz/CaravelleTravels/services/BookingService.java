package ru.almaz.CaravelleTravels.services;

import ru.almaz.CaravelleTravels.entities.Booking;
import ru.almaz.CaravelleTravels.entities.BookingStatus;
import ru.almaz.CaravelleTravels.entities.User;

import java.util.Date;
import java.util.List;

public interface BookingService {
    void createNewBooking(User user);

    Booking getBookingById(Long id);

    void setDateColumn(User user, String value);
    void setFromColumn(User user, String value);
    void setToColumn(User user, String value);
    void setPhoneColumn(User user, String value);
    void setNameColumn(User user, String value);
    void setPassengersColumn(User user, String value);
    void update(Long id, Booking booking);
    void setStatus(User user, BookingStatus status);
    void setStatus(Booking booking, BookingStatus status);

    Booking getFirstUserBookingByStatus(BookingStatus status, User user);
    void deleteBooking(Long id);

    List<Booking> findAll();

    List<Booking> findAllReverseOrder();

    List<Booking> findAllByDate(Date date);
}
