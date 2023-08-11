package ru.almaz.CaravelleTravels;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.almaz.CaravelleTravels.config.BotConfig;
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;

    public TelegramBot(BotConfig config) {
        super(config.getToken());
        this.config = config;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String text = update.getMessage().getText();
            System.out.println("Message text: " + text + ". Sender: " + update.getMessage().getChat().getUserName());

            switch (text) {
                case "/start":
                    startCommandReceived(update.getMessage().getChat().getFirstName(), chatId);
                    break;
                default:
                    sendMessage("Sorry, command not found", chatId);
                    break;
            }
        }
    }

    private void startCommandReceived(String userName, long chatId) {
        String answer = "Hello, " + userName + "!";

        sendMessage(answer, chatId);
    }

    private void sendMessage(String text, long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return config.getName();
    }
}
