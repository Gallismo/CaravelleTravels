package ru.almaz.CaravelleTravels.bot.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.almaz.CaravelleTravels.bot.Keyboards;
import ru.almaz.CaravelleTravels.bot.commands.abstr.MyCommand;
import ru.almaz.CaravelleTravels.config.MessagesText;
import ru.almaz.CaravelleTravels.entities.Booking;
import ru.almaz.CaravelleTravels.services.BookingService;
import ru.almaz.CaravelleTravels.services.UserService;

@Component
@Slf4j
public class GetBookingCommand extends MyCommand {
    private final UserService userService;
    private final BookingService bookingService;

    @Autowired
    public GetBookingCommand(UserService userService, BookingService bookingService) {
        super("find", "admin Команда для нахождения заявки по идентификатору\n<b>Для администратора</b> (Пример /find 1)");
        this.userService = userService;
        this.bookingService = bookingService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        log.info("BookingById command executed by user" + user + " by chat" + chat);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat.getId().toString());

        if (strings.length == 0) {
            sendMessage.setText(MessagesText.findByIdNotInputtedIdText);
            reply(absSender, sendMessage);
            return;
        }
        Long bookingId;
        try {
            bookingId = Long.parseLong(strings[0]);
        } catch (NumberFormatException e) {
            sendMessage.setText(MessagesText.notIntegerBookingIdErrorText);
            reply(absSender, sendMessage);
            return;
        }
        ru.almaz.CaravelleTravels.entities.User dbUser = userService.getUserByChatId(chat.getId());
        if (dbUser != null && dbUser.isPermissions()) {
            Booking booking = bookingService.getBookingById(bookingId);
            if (booking != null) {
                sendMessage.setText(booking.toMessage());
                sendMessage.setReplyMarkup(Keyboards.getProccesedButton(bookingId));
                reply(absSender, sendMessage);
            } else {
                sendMessage.setText(MessagesText.notFoundedByIdBookingText);
                reply(absSender, sendMessage);
            }
            return;
        }
        sendMessage.setText(MessagesText.noPermissionText);
        reply(absSender, sendMessage);
    }
}
