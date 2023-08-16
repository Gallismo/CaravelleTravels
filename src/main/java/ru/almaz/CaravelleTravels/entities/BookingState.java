package ru.almaz.CaravelleTravels.entities;

public enum BookingState {
    NONE("Ничего", "", "error"),
    DATE("date", ".+", "error"),
    FROM("from", ".+", "error"),
    TO("to", ".+", "error"),
    NAME("name", ".+", "error"),
    PHONE("phone", ".+", "error"),
    PASSENGERS("passengers", ".+", "error");

    private static final BookingState[] values = values();

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
