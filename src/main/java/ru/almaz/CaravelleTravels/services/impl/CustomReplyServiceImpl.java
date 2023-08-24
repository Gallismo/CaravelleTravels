package ru.almaz.CaravelleTravels.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.almaz.CaravelleTravels.config.RepliesText;
import ru.almaz.CaravelleTravels.entities.CustomBotReply;
import ru.almaz.CaravelleTravels.repositories.CustomReplyRepository;
import ru.almaz.CaravelleTravels.services.CustomReplyService;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CustomReplyServiceImpl implements CustomReplyService {

    private final CustomReplyRepository customReplyRepository;

    @Autowired
    public CustomReplyServiceImpl(CustomReplyRepository customReplyRepository) {
        this.customReplyRepository = customReplyRepository;
    }


    @Override
    public String findCustomTextOrDefault(RepliesText defaultText) {
        CustomBotReply customBotReply = customReplyRepository.findByDefaultText(defaultText).orElse(null);
        if (customBotReply == null || customBotReply.getCustomText() == null) {
            return defaultText.defaultText;
        }
        return customBotReply.getCustomText();
    }

    @Override
    @Transactional
    public void save(CustomBotReply newCustomBotReply) {
        customReplyRepository.save(newCustomBotReply);
    }

    @Override
    @Transactional
    public void update(Long id, CustomBotReply customBotReply) {
        customBotReply.setId(id);
        customReplyRepository.save(customBotReply);
    }

    @Override
    public List<CustomBotReply> findAll() {
        return customReplyRepository.findAll();
    }

    @Override
    public CustomBotReply findById(Long id) {
        return customReplyRepository.findById(id).orElse(null);
    }
}
