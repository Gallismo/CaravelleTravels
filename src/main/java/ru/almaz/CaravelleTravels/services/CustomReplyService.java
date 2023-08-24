package ru.almaz.CaravelleTravels.services;

import ru.almaz.CaravelleTravels.config.RepliesText;
import ru.almaz.CaravelleTravels.entities.CustomBotReply;

import java.util.List;

public interface CustomReplyService {
    String findCustomTextOrDefault(RepliesText defaultText);

    void save(CustomBotReply newCustomBotReply);

    void update(Long id, CustomBotReply customBotReply);

    List<CustomBotReply> findAll();

    CustomBotReply findById(Long id);
}
