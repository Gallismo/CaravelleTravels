package ru.almaz.CaravelleTravels.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.almaz.CaravelleTravels.bot.commands.*;
import ru.almaz.CaravelleTravels.bot.commands.abstr.MyCommand;
import ru.almaz.CaravelleTravels.config.BotConfig;
import ru.almaz.CaravelleTravels.entities.Answer;
import ru.almaz.CaravelleTravels.entities.Booking;
import ru.almaz.CaravelleTravels.entities.BookingStatus;
import ru.almaz.CaravelleTravels.services.AnswerService;
import ru.almaz.CaravelleTravels.services.BookingService;
import ru.almaz.CaravelleTravels.services.UserService;

import java.util.List;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingCommandBot {

    private final BotConfig config;
    private final BookingProcessRouter bookingProcessRouter;
    private final UserService userService;
    private final BookingService bookingService;
    private final AnswerService answerService;
    @Autowired
    public TelegramBot(BotConfig config, StartCommand startCommand,
                       BookingCommand bookingCommand, CancelBookingCommand cancelBookingCommand,
                       BookingProcessRouter bookingProcessRouter, BackCommand backCommand,
                       InformationCommand informationCommand, UserService userService,
                       BookingService bookingService, GetBookingCommand getBookingCommand,
                       QuestionsCommand questionsCommand, AnswerService answerService,
                       GetBookingsByDateCommand getBookingsByDateCommand) {
        super(config.getToken());
        this.config = config;
        this.bookingProcessRouter = bookingProcessRouter;
        this.userService = userService;
        this.bookingService = bookingService;
        this.answerService = answerService;
        register(startCommand);
        register(bookingCommand);
        register(cancelBookingCommand);
        register(backCommand);
        register(informationCommand);
        register(getBookingCommand);
        register(questionsCommand);
        register(getBookingsByDateCommand);
        register(new MyCommand("help", "Выводит список всех команд") {
            @Override
            public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
                execute(absSender, getCommands(chat.getId().toString()));
            }
        });
        registerDefaultAction(((absSender, message) -> sendMessage(new SendMessage(message.getChatId().toString(), "Unkown command"))));
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


    private void sendMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void sendMessages(List<SendMessage> messages) {
        for (SendMessage message : messages) {
            sendMessage(message);
        }
    }

    @Override
    public String getBotUsername() {
        return config.getName();
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            List<SendMessage> messages = bookingProcessRouter.processAndReturnMessages(update.getMessage().getChatId(), update.getMessage().getText());
            sendMessages(messages);
        }
        if (update.hasCallbackQuery()) {
            String callback = update.getCallbackQuery().getData();
            Message message = update.getCallbackQuery().getMessage();
            Long chatId = message.getChatId();

            if (callback.matches("processed_\\d+")) {
                Long bookingId = Long.parseLong(callback.split("_")[1]);
                ru.almaz.CaravelleTravels.entities.User user = userService.getUserByChatId(chatId);
                if (user != null && user.isPermissions()) {
                    Booking booking = bookingService.getBookingById(bookingId);
                    if (booking != null) {
                        bookingService.setStatus(booking, BookingStatus.PROCESSED);
                    }
                    DeleteMessage deleteMessage = new DeleteMessage(chatId.toString(), message.getMessageId());
                    try {
                        execute(deleteMessage);
                    } catch (TelegramApiException e) {
                        log.error(e.getMessage());
                    }
                    sendMessage(new SendMessage(chatId.toString(), "Заявка №" + bookingId + " обработана"));
                }
            }
            if (callback.matches("question_\\d+")) {
                Long questionId = Long.parseLong(callback.split("_")[1]);
                Answer answer = answerService.getById(questionId);
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                if (answer != null) {
                    sendMessage.setText(answer.getAnswer());
                } else {
                    sendMessage.setText("Непредвиденная ошибка. Ответ не найден.");
                }
                sendMessage(sendMessage);
            }
        }
    }
}
