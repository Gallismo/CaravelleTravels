package ru.almaz.CaravelleTravels.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import ru.almaz.CaravelleTravels.entities.Booking;
import ru.almaz.CaravelleTravels.entities.BookingState;
import ru.almaz.CaravelleTravels.entities.BookingStatus;
import ru.almaz.CaravelleTravels.entities.User;
import ru.almaz.CaravelleTravels.services.BookingService;
import ru.almaz.CaravelleTravels.services.UserService;

@Component
public class BookingProcessRouter {
    private final UserService userService;
    private final BookingService bookingService;

    @Autowired
    public BookingProcessRouter(UserService userService, BookingService bookingService) {
        this.userService = userService;
        this.bookingService = bookingService;
    }

    public SendMessage process(Long chatId, String messageText) {
        User user = userService.doesUserStartedBooking(chatId);
        if (user == null) {
            return new SendMessage(chatId.toString(), "Для ввода текстовых данных необходимо " +
                    "выполнить команду /start (если вы еще не выполняли её), " +
                    "затем начать процесс заполнения заявки на поездку");
        }

        if (!messageText.matches(user.getBookingState().getRegex())) {
            System.out.println(messageText.matches(user.getBookingState().getRegex()));
            System.out.println(user.getBookingState().getRegex());
            System.out.println(user.getBookingState().name());
            return new SendMessage(chatId.toString(), user.getBookingState().getRegexErrorMessage() + "regex");
        }


        setCurrentColumnValue(user, messageText);
        user = userService.setNextBookingState(chatId);

        if (user.getBookingState() == BookingState.NONE) {
            bookingService.setStatus(user, BookingStatus.CREATED);
            userService.clearProcessingBooking(chatId);
            SendMessage sendMessage = new SendMessage(chatId.toString(),
                    "Процесс заполнения заявки №" + user.getProcessingBooking() + " завершен, ожидайте звонка!");
            sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
            return sendMessage;
        }
        SendMessage sendMessage = new SendMessage(chatId.toString(), user.getBookingState().getMessageToSend());
        sendMessage.setReplyMarkup(Keyboards.getKeyboard("/cancel", "/back"));
        return sendMessage;
    }

    private void setCurrentColumnValue(User user, String value) {
        switch (user.getBookingState()) {
            case DATE -> bookingService.setDateColumn(user, value);
            case FROM -> bookingService.setFromColumn(user, value);
            case TO -> bookingService.setToColumn(user, value);
            case NAME -> bookingService.setNameColumn(user, value);
            case PHONE -> bookingService.setPhoneColumn(user, value);
            case PASSENGERS -> bookingService.setPassengersColumn(user, value);
        }
    }
}
