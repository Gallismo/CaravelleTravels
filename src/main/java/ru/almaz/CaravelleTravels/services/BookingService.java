package ru.almaz.CaravelleTravels.services;

import ru.almaz.CaravelleTravels.entities.Booking;
import ru.almaz.CaravelleTravels.entities.BookingStatus;
import ru.almaz.CaravelleTravels.entities.User;

public interface BookingService {
    void createNewBooking(User user);

    Booking getBookingById(Long id);

    void setDateColumn(User user, String value);

    void setFromColumn(User user, String value);
    void setToColumn(User user, String value);
    void setPhoneColumn(User user, String value);
    void setNameColumn(User user, String value);
    void setPassengersColumn(User user, String value);

    void setStatus(User user, BookingStatus status);
}
