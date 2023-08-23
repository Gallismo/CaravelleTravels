package ru.almaz.CaravelleTravels.entities;

import ru.almaz.CaravelleTravels.config.MessagesText;

import java.text.SimpleDateFormat;

public enum BookingState {
    NONE("Ничего", "", "error"),
    DATE(MessagesText.dateInputText,
            "\\d{2}\\.\\d{2}\\.\\d{4}",
            MessagesText.dateErrorText),
    FROM(MessagesText.fromInputText,
            ".+",
            MessagesText.fromErrorText),
    TO(MessagesText.toInputText,
            ".+",
            MessagesText.toErrorText),
    NAME(MessagesText.nameInputText,
            ".+",
            MessagesText.nameErrorText),
    PHONE(MessagesText.phoneInputText,
            "(^\\+?\\d-\\d{3}-\\d{3}-\\d{2}-\\d{2}$|^\\+?\\d \\d{3} \\d{3} \\d{2} \\d{2}$|^\\+?\\d{11}$)",
            // Формат +7-999-999-99-99 или 8-999-999-99-99
            MessagesText.phoneErrorText),
    PASSENGERS(MessagesText.passengersInputText,
            "\\d",
            MessagesText.passengersErrorText);

    private static final BookingState[] values = values();

    public static SimpleDateFormat dateFormatter() {
        return new SimpleDateFormat("dd.MM.yyyy");
    }

    private final String messageToSend;
    private final String regex;
    private final String regexErrorMessage;


    BookingState(String messageToSend, String regex, String regexErrorMessage) {
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

    public String getMessageToSend() {
        return messageToSend;
    }

    public String getRegex() {
        return regex;
    }

    public String getRegexErrorMessage() {
        return regexErrorMessage;
    }
}
