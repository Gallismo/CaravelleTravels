package ru.almaz.CaravelleTravels.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.almaz.CaravelleTravels.entities.Booking;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> getBookingById(Long id);
}
