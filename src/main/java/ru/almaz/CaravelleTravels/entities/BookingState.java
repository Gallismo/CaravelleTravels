package ru.almaz.CaravelleTravels.entities;

import java.text.SimpleDateFormat;

public enum BookingState {
    NONE("Ничего", "", "error"),
    DATE("Введите дату поездки строго в формате 01.01.2000.",
            "\\d{2}\\.\\d{2}\\.\\d{4}",
            "Ошибка! Введите дату поездки строго в формате 01.01.2000 " +
                    "(День и месяц в двухзначном целочисленном формате, " +
                    "год в четырехзначном целочисленном формате, " +
                    "разделяя знаком точки('.') день, месяц и год)! " +
                    "Например 14.12.2002 (Четырнадцатое декабря 2002 года)."),
    FROM("Введите место ПОСАДКИ (Откуда), расположенное строго вдоль маршрута поездки. " +
            "Для того, чтобы узнать, где проходит маршрут поездки, " +
            "воспользуйтесь командой /information.",
            ".+",
            "Ошибка ввода места посадки."),
    TO("Введите место ВЫСАДКИ (Куда), расположенное строго вдоль маршрута поездки. " +
            "Для того, чтобы узнать, где проходит маршрут поездки, " +
            "воспользуйтесь командой /information.",
            ".+",
            "Ошибка ввода места высадки."),
    NAME("Введите на чьё имя оформляется заявка (именно тот, кто поедет), " +
            "желательно в формате 'Фамилия И.О.'.",
            ".+",
            "Ошибка ввода имени."),
    // Формат +7-999-999-99-99 или 8-999-999-99-99
    PHONE("Введите контактный номер мобильного телефона человека, " +
            "на которого оформляется заявка (именно тот, кто поедет, " +
            "водитель свяжется с данным человеком для подтверждения заявки).\n" +
            "Допустимые форматы:" +
            "\n+7-999-999-99-99;\n8-999-999-99-99;\n+7 999 999 99 99;\n8 999 999 99 99.",
            "\\+?\\d[ -]?\\d{3}[ -]?\\d{3}[ -]?\\d{2}[ -]?\\d{2}",
            "Ошибка ввода номера телефона, введите номер телефона, " +
                    "строго соблюдая один из допустимых форматов!\n" +
                    "Допустимые форматы:" +
                    "\n+7-999-999-99-99;\n8-999-999-99-99;\n+7 999 999 99 99;\n8 999 999 99 99."),
    PASSENGERS("Введите количество желаемых мест (количество пассажиров) " +
            "в формате однозначного целого числа. Например 2 (два).",
            "\\d",
            "Ошибка ввода количества пассажиров, введите снова, " +
                    "соблюдая допустимый формат.");

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
