package ru.almaz.CaravelleTravels.bot.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.almaz.CaravelleTravels.bot.commands.abstr.MyCommand;
import ru.almaz.CaravelleTravels.entities.UpdatableContent;
import ru.almaz.CaravelleTravels.services.UpdatableContentService;

import java.util.List;

@Component
public class InformationCommand extends MyCommand {

    private final UpdatableContentService updatableContentService;

    @Autowired
    public InformationCommand(UpdatableContentService updatableContentService) {
        super("information", "Вывод основной информации");
        this.updatableContentService = updatableContentService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        List<UpdatableContent> updatableContents = updatableContentService.findAll();
        for (UpdatableContent updatableContent: updatableContents) {
            reply(absSender, new SendMessage(chat.getId().toString(), updatableContent.toMessage()));
        }
    }
}
