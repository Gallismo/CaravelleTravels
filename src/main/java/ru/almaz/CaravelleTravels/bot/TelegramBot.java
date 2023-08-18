package ru.almaz.CaravelleTravels.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.almaz.CaravelleTravels.bot.commands.*;
import ru.almaz.CaravelleTravels.config.BotConfig;

import java.util.Locale;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingCommandBot {

    private final BotConfig config;
    private final BookingProcessRouter bookingProcessRouter;
;
    @Autowired
    public TelegramBot(BotConfig config, StartCommand startCommand,
                       BookingCommand bookingCommand, CancelBookingCommand cancelBookingCommand,
                       BookingProcessRouter bookingProcessRouter, PermissionCommand permissionCommand,
                       BackCommand backCommand) {
        super(config.getToken());
        this.config = config;
        this.bookingProcessRouter = bookingProcessRouter;
        register(startCommand);
        register(bookingCommand);
        register(cancelBookingCommand);
        register(permissionCommand);
        register(backCommand);
        register(new MyCommand("help", "Выводит список всех команд") {
            @Override
            public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
                execute(absSender, getCommands(chat.getId().toString()));
            }
        });
        registerDefaultAction(((absSender, message) -> sendMessage("Unkown command", message.getChatId())));
    }

    private SendMessage getCommands(String chatId) {
        StringBuilder stringBuilder = new StringBuilder();
        for (IBotCommand command : getRegisteredCommands()) {
            stringBuilder.append("/").append("<b>").append(command.getCommandIdentifier()).append("</b>");
            stringBuilder.append(" - ").append(command.getDescription()).append('\n');
        }
        SendMessage sendMessage = new SendMessage(chatId, stringBuilder.toString());
        sendMessage.setParseMode("HTML");
        return sendMessage;
    }


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
        if (update.hasMessage() && update.getMessage().hasText()) {
            try {
                execute(bookingProcessRouter.process(update.getMessage().getChatId(), update.getMessage().getText()));
            } catch (TelegramApiException e) {
                log.error(e.getMessage());
            }
        }
        if (update.hasCallbackQuery()) {
            String callback = update.getCallbackQuery().getData();
            System.out.println(getRegisteredCommands());
        }
    }
}
