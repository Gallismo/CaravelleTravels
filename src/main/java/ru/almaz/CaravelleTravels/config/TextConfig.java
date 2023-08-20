package ru.almaz.CaravelleTravels.config;

import lombok.Data;

@Data
public class TextConfig {
    public static final String notStartedBotText = "Для запуска работы с ботом используйте команду /start";
    public static final String alreadyStartedBookingText = "Вы уже начали процесс записи на поездку.\n" +
            "Продолжите вводить данные, либо выполните команду /cancel.";
    public static final String doNotSpamText = "В целях избежания спама, действие было прервано по причине: " +
            "У вас имеется созданная и необработанная заявка.";
    public static final String startedBookingText = "Процесс заполнения заявки начат, номер заявки - ";
    public static final String notStartedBookingText = "Для ввода текстовых данных необходимо " +
            "выполнить команду /start (если вы еще не выполняли её), " +
            "затем начать процесс заполнения заявки на поездку";
    public static final String dateInputText = "Введите дату поездки строго в формате 01.01.2000.";
    public static final String dateErrorText = "Ошибка! Введите дату поездки строго в формате 01.01.2000 " +
            "(День и месяц в двухзначном целочисленном формате, " +
            "год в четырехзначном целочисленном формате, " +
            "разделяя знаком точки('.') день, месяц и год)! " +
            "Например 14.12.2002 (Четырнадцатое декабря 2002 года).";
    public static final String fromInputText = "Введите место ПОСАДКИ (Откуда), расположенное строго вдоль маршрута поездки. " +
            "Для того, чтобы узнать, где проходит маршрут поездки, " +
            "воспользуйтесь командой /information.";
    public static final String fromErrorText = "Ошибка ввода места посадки.";
    public static final String toInputText = "Введите место ВЫСАДКИ (Куда), расположенное строго вдоль маршрута поездки. " +
            "Для того, чтобы узнать, где проходит маршрут поездки, " +
            "воспользуйтесь командой /information.";
    public static final String toErrorText = "Ошибка ввода места высадки.";
    public static final String nameInputText = "Введите на чьё имя оформляется заявка (именно тот, кто поедет), " +
            "желательно в формате 'Фамилия И.О.'.";
    public static final String nameErrorText = "Ошибка ввода имени.";
    public static final String phoneInputText = "Введите контактный номер мобильного телефона человека, " +
            "на которого оформляется заявка (именно тот, кто поедет, " +
            "водитель свяжется с данным человеком для подтверждения заявки).\n" +
            "Допустимые форматы:" +
            "\n+7-XXX-XXX-XX-XX;\n+7 XXX XXX XX XX;\n+7XXXXXXXXXX;\n8-XXX-XXX-XX-XX;\n8 XXX XXX XX XX;\n8XXXXXXXXXX.";
    public static final String phoneErrorText = "Ошибка ввода номера телефона, введите номер телефона, " +
            "строго соблюдая один из допустимых форматов!\n" +
            "Допустимые форматы:" +
            "\n+7-XXX-XXX-XX-XX;\n+7 XXX XXX XX XX;\n+7XXXXXXXXXX;\n8-XXX-XXX-XX-XX;\n8 XXX XXX XX XX;\n8XXXXXXXXXX.";
    public static final String passengersInputText = "Введите количество желаемых мест (количество пассажиров) " +
            "в формате однозначного целого числа. Например 2 (два).";
    public static final String passengersErrorText = "Ошибка ввода количества пассажиров, введите снова, " +
            "соблюдая допустимый формат.";
    public static final String bookingCreatedPrefixText = "Процесс заполнения заявки №";
    public static final String bookingCreatedPostfixText = " завершен, ожидайте звонка!";
    public static final String notifyPrefixText = "Оформлена заявка №";
    public static final String questionsMessageText = "Часто задаваемые вопросы:";
    public static final String findByDateNotInputtedDateText = "Необходимо указать дату заявки (/getbooking XX.XX.XXXX).";
    public static final String notFoundedByDateBookingsText = "Заявок с такой датой не существует.";
    public static final String noPermissionText = "Вы не имеете прав доступа.";
    public static final String findByIdNotInputtedIdText = "Необходимо указать номер заявки (/getbooking X).";
    public static final String notIntegerBookingIdErrorText = "Номер заявки необходимо указать в виде целого числа.";
    public static final String notFoundedByIdBookingText = "Заявки с таким номером не существует.";
    public static final String noBookingProcessText = "Вы не начинали заполнять заявку на поездку.\n" +
            "Для создания заявки воспользуйтесь командой /booking";
    public static final String canceledBookingText = "Процесс заполнения заявки прерван.";
    public static final String bookingUnexpectedErrorText = "Произошла непредвиденная ошибка, начните заполнения заявки сначала командой /booking";
    public static final String startCommandText = "Вас приветствует Caravelle Travels Уфа-Учалы бот, тут вы можете:" +
            "\n- Оставить заявку на поездку, после этого вам перезвонят для потверждения заявки" +
            "\n- Узнать всю основную информацию о поездке" +
            "\n- Найти ответы на часто задаваемые вопросы" +
            "\n\n Для того, чтобы узнать список команд, введите /help.";

}
