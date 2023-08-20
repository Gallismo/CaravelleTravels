package ru.almaz.CaravelleTravels.bot.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.almaz.CaravelleTravels.bot.commands.abstr.MyCommand;
import ru.almaz.CaravelleTravels.config.TextConfig;
import ru.almaz.CaravelleTravels.entities.BookingState;
import ru.almaz.CaravelleTravels.services.BookingService;
import ru.almaz.CaravelleTravels.services.UserService;

@Component
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
        String chatId = chat.getId().toString();
        ru.almaz.CaravelleTravels.entities.User dbUser = userService.getUserByChatId(chat.getId());

        if (dbUser == null) {
            reply(absSender, new SendMessage(chatId, TextConfig.notStartedBotText));
            return;
        }

//        if (strings.length > 0) {
//            Long bookingId;
//            try {
//                bookingId = Long.parseLong(strings[0]);
//            } catch (NumberFormatException e) {
//                execute(absSender, new SendMessage(chatId, "Для отмены ранее заполненной заявки " +
//                        "необходимо указать номер заявки в формате целого числа. Например: /cancel 1"));
//                return;
//            }
//            Booking booking = bookingService.getBookingById(bookingId);
//            if (booking == null) {
//                execute(absSender, new SendMessage(chatId, "Заявка №" + bookingId + " не существует."));
//                return;
//            }
//            if (Objects.equals(booking.getUser().getId(), dbUser.getId())) {
//                bookingService.deleteBooking(bookingId);
//                execute(absSender, new SendMessage(chatId, "Заявка №" + bookingId + " успешно отменена."));
//                // уведомление об отмене заявки
//                return;
//            }
//
//            execute(absSender, new SendMessage(chatId, "Заявка была создана не вами, отмена заявки не удалась."));
//            return;
//        }

        if (dbUser.getBookingState() == BookingState.NONE) {
            reply(absSender, new SendMessage(chatId, TextConfig.noBookingProcessText));
            return;
        }

        bookingService.deleteBooking(dbUser.getProcessingBooking());
        userService.clearProcessingBooking(chat.getId());
        SendMessage sendMessage = new SendMessage(chatId,TextConfig.canceledBookingText);
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
        reply(absSender, sendMessage);
    }
}
