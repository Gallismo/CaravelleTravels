package ru.almaz.CaravelleTravels.bot.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.almaz.CaravelleTravels.bot.commands.abstr.MyCommand;
import ru.almaz.CaravelleTravels.config.RepliesText;
import ru.almaz.CaravelleTravels.services.CustomReplyService;
import ru.almaz.CaravelleTravels.services.UserService;

@Component
public class HelpCommand extends MyCommand {

    private final UserService userService;
    private final CustomReplyService customReplyService;

    @Autowired
    public HelpCommand(UserService userService, CustomReplyService customReplyService) {
        super("help", "Список всех команд");
        this.userService = userService;
        this.customReplyService = customReplyService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        ru.almaz.CaravelleTravels.entities.User dbUser = userService.getUserByChatId(chat.getId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat.getId());

        if (dbUser != null && dbUser.isPermissions()) {
            sendMessage.setText(customReplyService.findCustomTextOrDefault(RepliesText.helpCommandAdminText));
        } else {
            sendMessage.setText(customReplyService.findCustomTextOrDefault(RepliesText.helpCommandText));
        }

        reply(absSender, sendMessage);
    }
}
