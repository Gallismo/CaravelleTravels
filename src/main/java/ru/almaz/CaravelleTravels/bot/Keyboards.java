package ru.almaz.CaravelleTravels.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.almaz.CaravelleTravels.entities.Answer;

import java.util.ArrayList;
import java.util.List;

public class Keyboards {
    public static ReplyKeyboardMarkup getReplyKeyboard(String ...buttons) {
        KeyboardRow keyboardRow = new KeyboardRow();
        for (int i = 0; i < buttons.length; i++) {
            keyboardRow.add(buttons[i]);
        }
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        keyboardRowList.add(keyboardRow);
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(keyboardRowList);
        keyboardMarkup.setResizeKeyboard(true);
        return keyboardMarkup;
    }

    public static InlineKeyboardMarkup getProccesedButton(Long bookingId) {
        InlineKeyboardButton button = new InlineKeyboardButton("Обработано");
        button.setCallbackData("processed_" + bookingId);
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button);
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup getQuestionsKeyboard(List<Answer> answers) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        for (Answer answer : answers) {
            InlineKeyboardButton button = new InlineKeyboardButton(answer.getQuestion());
            button.setCallbackData("question_" + answer.getId());
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(button);
            rows.add(row);
        }
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }
}
