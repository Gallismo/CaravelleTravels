package ru.almaz.CaravelleTravels.bot.commands.abstr;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public abstract class MyCommand extends BotCommand {

    public MyCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

//    DEPRECATED
//    protected void reply(AbsSender absSender, BotApiMethod<Message> method) {
//        try {
//            absSender.execute(method);
//        } catch (TelegramApiException e) {
//            log.error(e.getMessage());
//        }
//    }

    protected void reply(AbsSender absSender, SendMessage message) {
        message.setParseMode("HTML");
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
