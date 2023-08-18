package ru.almaz.CaravelleTravels.bot.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.almaz.CaravelleTravels.bot.MyCommand;
@Component
public class PermissionCommand extends MyCommand {

    public PermissionCommand() {
        super("permission", "Команда для активации супер-пользователя");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        execute(absSender, new SendMessage(chat.getId().toString(), "тест"));
    }
}
