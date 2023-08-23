package ru.almaz.CaravelleTravels.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.almaz.CaravelleTravels.entities.Booking;
import ru.almaz.CaravelleTravels.entities.BookingState;
import ru.almaz.CaravelleTravels.entities.BookingStatus;
import ru.almaz.CaravelleTravels.entities.User;
import ru.almaz.CaravelleTravels.repositories.BookingRepository;
import ru.almaz.CaravelleTravels.repositories.UserRepository;
import ru.almaz.CaravelleTravels.services.BookingService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        user.setProcessingBooking(booking.getId());
        userRepository.save(user);
    }

    @Override
    public Booking getBookingById(Long id) {
        Optional<Booking> booking = bookingRepository.getBookingById(id);
        return booking.orElse(null);
    }

    @Override
    @Transactional
    public void update(Long id, Booking booking) {
        booking.setId(id);
        bookingRepository.save(booking);
    }

    @Override
    @Transactional
    public void setStatus(Booking booking, BookingStatus status) {
        booking.setBookingStatus(status);
        bookingRepository.save(booking);
    }

    @Override
    public Booking getFirstUserBookingByStatus(BookingStatus status, User user) {
        List<Booking> bookingList = bookingRepository.getUserBookingsByStatus(status, user);
        if (bookingList.size() > 0) {
            return bookingList.get(0);
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteBooking(Long id) {
        Booking booking = getBookingById(id);
        if (booking == null) return;
        bookingRepository.delete(booking);
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public List<Booking> findAllReverseOrder() {
        return bookingRepository.findAllOrderReversed();
    }

    @Override
    public List<Booking> findAllByDate(Date date) {
        return bookingRepository.findAllByDate(date);
    }


}
