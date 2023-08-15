package ru.almaz.CaravelleTravels.bot;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.almaz.CaravelleTravels.services.UserService;

@Slf4j
public abstract class MyCommand extends BotCommand {

    public MyCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    public void execute(AbsSender absSender, BotApiMethod<Message> method) {
        try {
            absSender.execute(method);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}