package ru.almaz.CaravelleTravels.services;

import org.springframework.stereotype.Service;
import ru.almaz.CaravelleTravels.entities.User;

import java.util.List;

public interface UserService {
    void saveNewBotUser(Long chatId);

    User getUserByChatId(Long chatId);

    void save(User user);

    void update(Long id, User user);
    User doesUserStartedBooking(Long chatId);

    User setNextBookingState(Long chatId);

    User setPrevBookingState(Long chatId);

    void clearProcessingBooking(Long chatId);

    List<User> findAllByPermission(boolean isHavePermission);
}
