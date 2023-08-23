package ru.almaz.CaravelleTravels.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.almaz.CaravelleTravels.entities.BookingState;
import ru.almaz.CaravelleTravels.entities.User;
import ru.almaz.CaravelleTravels.repositories.UserRepository;
import ru.almaz.CaravelleTravels.services.UserService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void saveNewBotUser(Long chatId, String telegramUserName) {
        User newUser = new User(chatId);
        newUser.setTelegramUserName(telegramUserName);
        userRepository.save(newUser);
    }

    @Override
    public User getUserByChatId(Long chatId) {
        Optional<User> user = userRepository.findUserByChatId(chatId);
        return user.orElse(null);
    }

    @Override
    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(Long id, User user) {
        user.setId(id);
        userRepository.save(user);
    }

    @Override
    public User doesUserStartedBooking(Long chatId) {
        Optional<User> user = userRepository.findUserByChatId(chatId);
        return user.filter(value -> value.getBookingState() != BookingState.NONE).orElse(null);
    }

    @Override
    @Transactional
    public User setNextBookingState(Long chatId) {
        User user = getUserByChatId(chatId);
        if (user == null) return null;
        user.setBookingState(user.getBookingState().next());
        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    public User setPrevBookingState(Long chatId) {
        User user = getUserByChatId(chatId);
        if (user == null) return null;
        if (user.getBookingState().prev() != BookingState.NONE) {
            user.setBookingState(user.getBookingState().prev());
            userRepository.save(user);
        }
        return user;
    }

    @Override
    @Transactional
    public void clearProcessingBooking(Long chatId) {
        User user = getUserByChatId(chatId);
        if (user == null) return;
        user.setBookingState(BookingState.NONE);
        user.setProcessingBooking(null);
        userRepository.save(user);
    }

    @Override
    public List<User> findAllByPermission(boolean isHavePermission) {
        return userRepository.findAllByPermissions(isHavePermission);
    }

    @Override
    @Transactional
    public void givePermissionForChatId(User newUser) {
        User isExistUser = getUserByChatId(newUser.getChatId());
        if (isExistUser == null) {
            userRepository.save(newUser);
        } else {
            isExistUser.setPermissions(true);
            userRepository.save(isExistUser);
        }
    }
}
