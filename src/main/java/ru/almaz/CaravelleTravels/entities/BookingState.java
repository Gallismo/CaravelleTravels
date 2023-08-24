package ru.almaz.CaravelleTravels.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.almaz.CaravelleTravels.config.RepliesText;

import java.text.SimpleDateFormat;


public enum BookingState {
    NONE(null, "", null),
    DATE(RepliesText.dateInputText,
            "\\d{2}\\.\\d{2}\\.\\d{4}",
            RepliesText.dateErrorText),
    FROM(RepliesText.fromInputText,
            ".+",
            RepliesText.fromErrorText),
    TO(RepliesText.toInputText,
            ".+",
            RepliesText.toErrorText),
    NAME(RepliesText.nameInputText,
            ".+",
            RepliesText.nameErrorText),
    PHONE(RepliesText.phoneInputText,
            "(^\\+?\\d-\\d{3}-\\d{3}-\\d{2}-\\d{2}$|^\\+?\\d \\d{3} \\d{3} \\d{2} \\d{2}$|^\\+?\\d{11}$)",
            // Формат +7-999-999-99-99 или 8-999-999-99-99
            RepliesText.phoneErrorText),
    PASSENGERS(RepliesText.passengersInputText,
            "\\d",
            RepliesText.passengersErrorText);

    private static final BookingState[] values = values();

    public static SimpleDateFormat dateFormatter() {
        return new SimpleDateFormat("dd.MM.yyyy");
    }

    private final RepliesText messageToSend;
    private final String regex;
    private final RepliesText regexErrorMessage;

    BookingState(RepliesText messageToSend, String regex, RepliesText regexErrorMessage) {
        this.messageToSend = messageToSend;
        this.regex = regex;
        this.regexErrorMessage = regexErrorMessage;
    }

    public BookingState next() {
        return values[(this.ordinal() + 1) % values.length];
    }
    public BookingState prev() {
        return values[(this.ordinal() - 1) % values.length];
    }

    public RepliesText getMessageToSend() {
        return messageToSend;
    }

    public String getRegex() {
        return regex;
    }

    public RepliesText getRegexErrorMessage() {
        return regexErrorMessage;
    }
}
