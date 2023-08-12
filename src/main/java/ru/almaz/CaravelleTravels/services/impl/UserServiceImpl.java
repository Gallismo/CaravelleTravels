package ru.almaz.CaravelleTravels.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.almaz.CaravelleTravels.entities.User;
import ru.almaz.CaravelleTravels.repositories.UserRepository;
import ru.almaz.CaravelleTravels.services.UserService;

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
    public void saveNewBotUser(Long chatId) {
        User newUser = new User(chatId);
        userRepository.save(newUser);
    }
}
