package ru.almaz.CaravelleTravels.bot.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.almaz.CaravelleTravels.bot.commands.abstr.MyCommand;
import ru.almaz.CaravelleTravels.config.MessagesText;
import ru.almaz.CaravelleTravels.entities.BookingState;
import ru.almaz.CaravelleTravels.services.BookingService;
import ru.almaz.CaravelleTravels.services.UserService;

@Component
@Slf4j
public class CancelBookingCommand extends MyCommand {
    private final UserService userService;
    private final BookingService bookingService;

    @Autowired
    public CancelBookingCommand(UserService userService, BookingService bookingService) {
        super("cancel", "Отмена заполнения заявки и её удаление");
        this.userService = userService;
        this.bookingService = bookingService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        log.info("Cancel command executed by user" + user + " by chat" + chat);

        String chatId = chat.getId().toString();
        ru.almaz.CaravelleTravels.entities.User dbUser = userService.getUserByChatId(chat.getId());

        if (dbUser == null) {
            reply(absSender, new SendMessage(chatId, MessagesText.notStartedBotText));
            return;
        }

        if (dbUser.getBookingState() == BookingState.NONE) {
            reply(absSender, new SendMessage(chatId, MessagesText.noBookingProcessText));
            return;
        }

        bookingService.deleteBooking(dbUser.getProcessingBooking());
        userService.clearProcessingBooking(chat.getId());
        SendMessage sendMessage = new SendMessage(chatId, MessagesText.canceledBookingText);
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
        reply(absSender, sendMessage);
    }
}
