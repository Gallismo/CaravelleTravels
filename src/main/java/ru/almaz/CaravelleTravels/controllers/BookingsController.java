package ru.almaz.CaravelleTravels.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.almaz.CaravelleTravels.entities.Booking;
import ru.almaz.CaravelleTravels.services.BookingService;

import java.util.List;

@Controller
@Slf4j
public class BookingsController {
    private final BookingService bookingService;

    @Autowired
    public BookingsController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/")
    public String allBookingsPage(Model model) {
        log.info("Bookings show all page opened");

        List<Booking> bookingList = bookingService.findAllReverseOrder();
        model.addAttribute("bookings", bookingList);
        return "bookings";
    }
}
