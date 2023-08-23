package ru.almaz.CaravelleTravels.services;

import ru.almaz.CaravelleTravels.entities.Booking;
import ru.almaz.CaravelleTravels.entities.BookingStatus;
import ru.almaz.CaravelleTravels.entities.User;

import java.util.Date;
import java.util.List;

public interface BookingService {
    void createNewBooking(User user);
    Booking getBookingById(Long id);
    void update(Long id, Booking booking);
    void setStatus(Booking booking, BookingStatus status);
    Booking getFirstUserBookingByStatus(BookingStatus status, User user);
    void deleteBooking(Long id);
    List<Booking> findAll();
    List<Booking> findAllReverseOrder();
    List<Booking> findAllByDate(Date date);
}
