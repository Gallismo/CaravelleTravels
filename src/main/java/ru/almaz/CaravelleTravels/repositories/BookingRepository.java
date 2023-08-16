package ru.almaz.CaravelleTravels.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.almaz.CaravelleTravels.entities.Booking;
import ru.almaz.CaravelleTravels.entities.BookingStatus;
import ru.almaz.CaravelleTravels.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> getBookingById(Long id);
    @Query(value = "SELECT b FROM Booking b WHERE b.bookingStatus = ?1 and b.user = ?2")
    List<Booking> getUserBookingsByStatus(BookingStatus status, User user);
}
