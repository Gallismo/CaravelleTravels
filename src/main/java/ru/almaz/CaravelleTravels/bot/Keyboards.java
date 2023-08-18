package ru.almaz.CaravelleTravels.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class Keyboards {
    public static ReplyKeyboardMarkup getKeyboard(String ...buttons) {
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
}
