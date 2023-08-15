package ru.almaz.CaravelleTravels.services;

import org.springframework.stereotype.Service;
import ru.almaz.CaravelleTravels.entities.User;

public interface UserService {
    void saveNewBotUser(Long chatId);

    User getUserByChatId(Long chatId);

    void save(User user);
    boolean doesUserStartedBooking(Long chatId);
}
