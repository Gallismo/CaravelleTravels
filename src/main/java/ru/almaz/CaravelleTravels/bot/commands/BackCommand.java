package ru.almaz.CaravelleTravels.bot.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.almaz.CaravelleTravels.bot.commands.abstr.MyCommand;
import ru.almaz.CaravelleTravels.services.UserService;

@Component
public class BackCommand extends MyCommand {
    private final UserService userService;

    @Autowired
    public BackCommand(UserService userService) {
        super("back", "Возвращает к предыдущему шагу заполнения заявки");
        this.userService = userService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        ru.almaz.CaravelleTravels.entities.User dbUser = userService.doesUserStartedBooking(chat.getId());
        if (dbUser == null) {
            execute(absSender, new SendMessage(chat.getId().toString(), "Выполнение данной команды " +
                    "доступно только во время заполнения заявки!"));
        }

        dbUser = userService.setPrevBookingState(chat.getId());
        execute(absSender, new SendMessage(chat.getId().toString(), dbUser.getBookingState().getMessageToSend()));
    }
}
