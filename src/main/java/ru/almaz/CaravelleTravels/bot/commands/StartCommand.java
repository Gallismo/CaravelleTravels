package ru.almaz.CaravelleTravels.bot.commands;


import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class StartCommand extends MyCommand {

    private final UserService userService;
    private final CustomReplyService customReplyService;
    @Autowired
    public StartCommand(UserService userService, CustomReplyService customReplyService) {
        super("start", "Команда для начала работы с ботом");
        this.userService = userService;
        this.customReplyService = customReplyService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        log.info("Start command executed by user" + user + " by chat" + chat);
        String text = customReplyService.findCustomTextOrDefault(RepliesText.startCommandText);
        if (userService.getUserByChatId(chat.getId()) == null) {
            userService.saveNewBotUser(chat.getId(), user.getUserName());
        }
        SendMessage message = new SendMessage(chat.getId().toString(), text);
        reply(absSender, message);
    }
}
