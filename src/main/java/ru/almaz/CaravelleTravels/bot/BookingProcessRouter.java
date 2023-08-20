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

import java.util.ArrayList;
import java.util.List;

@Component
public class BookingProcessRouter {
    private final UserService userService;
    private final BookingService bookingService;

    @Autowired
    public BookingProcessRouter(UserService userService, BookingService bookingService) {
        this.userService = userService;
        this.bookingService = bookingService;
    }

    public List<SendMessage> processAndReturnMessages(Long chatId, String messageText) {
        List<SendMessage> messages = new ArrayList<>();
        User user = userService.doesUserStartedBooking(chatId);
        if (user == null) {
            SendMessage sendMessage = new SendMessage(chatId.toString(), "Для ввода текстовых данных необходимо " +
                    "выполнить команду /start (если вы еще не выполняли её), " +
                    "затем начать процесс заполнения заявки на поездку");
            messages.add(sendMessage);
            return messages;
        }

        if (!messageText.matches(user.getBookingState().getRegex())) {
            SendMessage sendMessage = new SendMessage(chatId.toString(), user.getBookingState().getRegexErrorMessage());
            messages.add(sendMessage);
            return messages;
        }


        setCurrentColumnValue(user, messageText);
        user = userService.setNextBookingState(chatId);

        if (user.getBookingState() == BookingState.NONE) {
            Booking booking = bookingService.getBookingById(user.getProcessingBooking());
            addNotifySuperUsersMessages(messages, booking);

            bookingService.setStatus(user, BookingStatus.CREATED);
            userService.clearProcessingBooking(chatId);
            SendMessage sendMessage = new SendMessage(chatId.toString(),
                    "Процесс заполнения заявки №" + user.getProcessingBooking() + " завершен, ожидайте звонка!");
            sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
            messages.add(sendMessage);
            return messages;
        }

        SendMessage sendMessage = new SendMessage(chatId.toString(), user.getBookingState().getMessageToSend());
        sendMessage.setReplyMarkup(Keyboards.getReplyKeyboard("/cancel", "/back"));
        messages.add(sendMessage);
        return messages;
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

    private void addNotifySuperUsersMessages(List<SendMessage> messages, Booking booking) {
        List<User> superUsers = userService.findAllByPermission(true);
        if (superUsers.size() > 0) {
            StringBuilder messageText = new StringBuilder();
            messageText.append("Оформлена заявка №").append(booking.getId()).append('\n').append(booking.toMessage());
            for (User user : superUsers) {
                SendMessage sendMessage = new SendMessage(user.getChatId().toString(), messageText.toString());
                sendMessage.setReplyMarkup(Keyboards.getProccesedButton(booking.getId()));
                messages.add(sendMessage);
            }
        }
    }
}
