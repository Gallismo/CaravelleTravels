package ru.almaz.CaravelleTravels.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import ru.almaz.CaravelleTravels.config.TextConfig;
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
            SendMessage sendMessage = new SendMessage(chatId.toString(), TextConfig.notStartedBookingText);
            messages.add(sendMessage);
            return messages;
        }

        if (!messageText.matches(user.getBookingState().getRegex())) {
            SendMessage sendMessage = new SendMessage(chatId.toString(), user.getBookingState().getRegexErrorMessage());
            messages.add(sendMessage);
            return messages;
        }

        Booking booking = bookingService.getBookingById(user.getProcessingBooking());
        if (booking == null) {
            userService.clearProcessingBooking(chatId);
            SendMessage sendMessage = new SendMessage(chatId.toString(), TextConfig.bookingUnexpectedErrorText);
            messages.add(sendMessage);
            return messages;
        }

        setCurrentColumnValue(user, booking, messageText);
        user.setBookingState(user.getBookingState().next());
        userService.update(user.getId(), user);

        if (user.getBookingState() == BookingState.NONE) {
            addNotifySuperUsersMessages(messages, booking);

            booking.setBookingStatus(BookingStatus.CREATED);
            user.setProcessingBooking(null);
            user.setBookingState(BookingState.NONE);

            SendMessage sendMessage = new SendMessage(chatId.toString(),
                     TextConfig.bookingCreatedPrefixText + user.getProcessingBooking() + TextConfig.bookingCreatedPostfixText);
            sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
            messages.add(sendMessage);

            return messages;
        }
        bookingService.update(booking.getId(), booking);
        userService.update(user.getId(), user);

        SendMessage sendMessage = new SendMessage(chatId.toString(), user.getBookingState().getMessageToSend());
        sendMessage.setReplyMarkup(Keyboards.getReplyKeyboard("/cancel", "/back"));
        messages.add(sendMessage);
        return messages;
    }

    private void setCurrentColumnValue(User user, Booking booking, String value) {
        switch (user.getBookingState()) {
            case DATE -> booking.setDate(value);
            case FROM -> booking.setFromPlace(value);
            case TO -> booking.setToPlace(value);
            case NAME -> booking.setPassengerName(value);
            case PHONE -> booking.setPhoneNumber(value);
            case PASSENGERS -> booking.setPassengersCount(Integer.parseInt(value));
        }
    }

    private void addNotifySuperUsersMessages(List<SendMessage> messages, Booking booking) {
        List<User> superUsers = userService.findAllByPermission(true);
        if (superUsers.size() > 0) {
            StringBuilder messageText = new StringBuilder();
            messageText.append(TextConfig.notifyPrefixText).append(booking.getId()).append('\n').append(booking.toMessage());
            for (User user : superUsers) {
                SendMessage sendMessage = new SendMessage(user.getChatId().toString(), messageText.toString());
                sendMessage.setReplyMarkup(Keyboards.getProccesedButton(booking.getId()));
                messages.add(sendMessage);
            }
        }
    }
}
