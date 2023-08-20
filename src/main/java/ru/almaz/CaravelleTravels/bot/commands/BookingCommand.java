package ru.almaz.CaravelleTravels.bot.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.almaz.CaravelleTravels.bot.Keyboards;
import ru.almaz.CaravelleTravels.bot.commands.abstr.MyCommand;
import ru.almaz.CaravelleTravels.config.TextConfig;
import ru.almaz.CaravelleTravels.entities.BookingState;
import ru.almaz.CaravelleTravels.entities.BookingStatus;
import ru.almaz.CaravelleTravels.services.BookingService;
import ru.almaz.CaravelleTravels.services.UserService;

@Component
public class BookingCommand extends MyCommand {

    private final UserService userService;
    private final BookingService bookingService;

    @Autowired
    public BookingCommand(UserService userService, BookingService bookingService) {
        super("booking", "Начать заполнения заявки на поездку");
        this.userService = userService;
        this.bookingService = bookingService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String chatId = chat.getId().toString();
        ru.almaz.CaravelleTravels.entities.User dbUser = userService.getUserByChatId(chat.getId());

        if (dbUser == null) {
            reply(absSender, new SendMessage(chatId, TextConfig.notStartedBotText));
            return;
        }

        if (dbUser.getBookingState() != BookingState.NONE) {
            reply(absSender, new SendMessage(chatId, TextConfig.alreadyStartedBookingText));
            return;
        }
        if (bookingService.getFirstUserBookingByStatus(BookingStatus.CREATED, dbUser) != null) {
            reply(absSender, new SendMessage(chatId, TextConfig.doNotSpamText));
            return;
        }

        bookingService.createNewBooking(dbUser);

        reply(absSender, new SendMessage(chatId, TextConfig.startedBookingText + dbUser.getProcessingBooking()));
        SendMessage sendMessage = new SendMessage(chatId, dbUser.getBookingState().getMessageToSend());

        sendMessage.setReplyMarkup(Keyboards.getReplyKeyboard("/cancel"));
        reply(absSender, sendMessage);
    }
}