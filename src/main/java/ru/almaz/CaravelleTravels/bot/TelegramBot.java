package ru.almaz.CaravelleTravels.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.almaz.CaravelleTravels.bot.commands.StartCommand;
import ru.almaz.CaravelleTravels.config.BotConfig;
import ru.almaz.CaravelleTravels.services.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingCommandBot {

    private final BotConfig config;
;
    @Autowired
    public TelegramBot(BotConfig config, UserService userService) {
        super(config.getToken());
        this.config = config;
        register(new StartCommand(userService));
        registerDefaultAction(((absSender, message) -> sendMessage("Unkown command", message.getChatId())));
    }

//    @Override
//    public void onUpdateReceived(Update update) {
//        if (update.hasMessage() && update.getMessage().hasText()) {
//
//        }
//    }

//    private void startCommandReceived(String userName, Long chatId) {
//        String answer = "Hello, " + userName + "!";
//
//        userService.saveNewBotUser(chatId);
//        sendMessage(answer, chatId);
//        log.info("Replied to user " + userName + ", saved new user");
//    }


    private void sendMessage(String text, Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return config.getName();
    }

    @Override
    public void processNonCommandUpdate(Update update) {

    }
}
