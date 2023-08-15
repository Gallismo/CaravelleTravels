package ru.almaz.CaravelleTravels.bot.commands;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.almaz.CaravelleTravels.bot.MyCommand;
import ru.almaz.CaravelleTravels.services.UserService;
@Component
public class StartCommand extends MyCommand {

    private final UserService userService;
    @Autowired
    public StartCommand(UserService userService) {
        super("start", "Команда для начала работы с ботом");
        this.userService = userService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String answer = "Hi, " + user.getFirstName() + " " + user.getLastName() + "!";
        userService.saveNewBotUser(chat.getId());
        SendMessage message = new SendMessage(chat.getId().toString(), answer);
        execute(absSender, message);
    }
}
