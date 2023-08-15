package ru.almaz.CaravelleTravels.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.almaz.CaravelleTravels.entities.Booking;
import ru.almaz.CaravelleTravels.entities.BookingState;
import ru.almaz.CaravelleTravels.entities.User;
import ru.almaz.CaravelleTravels.repositories.BookingRepository;
import ru.almaz.CaravelleTravels.repositories.UserRepository;
import ru.almaz.CaravelleTravels.services.BookingService;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void createNewBooking(User user) {
        Booking booking = new Booking();
        booking.setUser(user);
        bookingRepository.save(booking);
        user.setBookingState(user.getBookingState().next());
        userRepository.save(user);
    }
}
