package ru.almaz.CaravelleTravels.bot.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.almaz.CaravelleTravels.bot.Keyboards;
import ru.almaz.CaravelleTravels.bot.commands.abstr.MyCommand;
import ru.almaz.CaravelleTravels.entities.Booking;
import ru.almaz.CaravelleTravels.entities.BookingState;
import ru.almaz.CaravelleTravels.services.BookingService;
import ru.almaz.CaravelleTravels.services.UserService;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Component
public class GetBookingsByDateCommand extends MyCommand {
    private final UserService userService;
    private final BookingService bookingService;

    @Autowired
    public GetBookingsByDateCommand(UserService userService, BookingService bookingService) {
        super("getbookingsbydate", "Команда для получения заявок по дате");
        this.userService = userService;
        this.bookingService = bookingService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat.getId().toString());

        if (strings.length == 0) {
            sendMessage.setText("Необходимо указать дату заявки (/getbooking XX.XX.XXXX).");
            execute(absSender, sendMessage);
            return;
        }
        Date bookingDate;
        try {
            bookingDate = BookingState.dateFormatter().parse(strings[0]);
        } catch (ParseException e) {
            sendMessage.setText("Дату заявки необходимо указать в формате XX.XX.XXXX");
            execute(absSender, sendMessage);
            return;
        }
        ru.almaz.CaravelleTravels.entities.User dbUser = userService.getUserByChatId(chat.getId());
        if (dbUser != null && dbUser.isPermissions()) {
            List<Booking> bookings = bookingService.findAllByDate(bookingDate);
            if (bookings.size() > 0) {
                for (Booking booking: bookings) {
                    sendMessage.setText(booking.toMessage());
                    sendMessage.setReplyMarkup(Keyboards.getProccesedButton(booking.getId()));
                    execute(absSender, sendMessage);
                }
            } else {
                sendMessage.setText("Заявок с такой датой не существует.");
                execute(absSender, sendMessage);
            }
            return;
        }
        sendMessage.setText("Вы не имеете прав доступа.");
        execute(absSender, sendMessage);
    }
}
