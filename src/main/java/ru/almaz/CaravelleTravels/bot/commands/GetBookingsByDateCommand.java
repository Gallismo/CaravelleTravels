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
import ru.almaz.CaravelleTravels.entities.BookingState;
import ru.almaz.CaravelleTravels.services.BookingService;
import ru.almaz.CaravelleTravels.services.UserService;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class GetBookingsByDateCommand extends MyCommand {
    private final UserService userService;
    private final BookingService bookingService;

    @Autowired
    public GetBookingsByDateCommand(UserService userService, BookingService bookingService) {
        super("date", "admin Команда для нахождения заявок по дате\n<b>Для администратора</b> (Пример /date 01.01.2000)");
        this.userService = userService;
        this.bookingService = bookingService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        log.info("BookingsByDate command executed by user" + user + " by chat" + chat);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat.getId().toString());

        if (strings.length == 0) {
            sendMessage.setText(MessagesText.findByDateNotInputtedDateText);
            reply(absSender, sendMessage);
            return;
        }
        Date bookingDate;
        try {
            bookingDate = BookingState.dateFormatter().parse(strings[0]);
        } catch (ParseException e) {
            sendMessage.setText(MessagesText.dateErrorText);
            reply(absSender, sendMessage);
            return;
        }
        ru.almaz.CaravelleTravels.entities.User dbUser = userService.getUserByChatId(chat.getId());
        if (dbUser != null && dbUser.isPermissions()) {
            List<Booking> bookings = bookingService.findAllByDate(bookingDate);
            if (bookings.size() > 0) {
                for (Booking booking: bookings) {
                    sendMessage.setText(booking.toMessage());
                    sendMessage.setReplyMarkup(Keyboards.getProccesedButton(booking.getId()));
                    reply(absSender, sendMessage);
                }
            } else {
                sendMessage.setText(MessagesText.notFoundedByDateBookingsText);
                reply(absSender, sendMessage);
            }
            return;
        }
        sendMessage.setText(MessagesText.noPermissionText);
        reply(absSender, sendMessage);
    }
}
