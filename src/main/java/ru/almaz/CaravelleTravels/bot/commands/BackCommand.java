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
public class BackCommand extends MyCommand {
    private final UserService userService;
    private final CustomReplyService customReplyService;

    @Autowired
    public BackCommand(UserService userService, CustomReplyService customReplyService) {
        super("back", "Возвращает к предыдущему шагу заполнения заявки");
        this.userService = userService;
        this.customReplyService = customReplyService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        log.info("Back command executed by user" + user + " by chat" + chat);


        ru.almaz.CaravelleTravels.entities.User dbUser = userService.doesUserStartedBooking(chat.getId());
        if (dbUser == null) {
            reply(absSender, new SendMessage(chat.getId().toString(), customReplyService.findCustomTextOrDefault(RepliesText.noBookingProcessText)));
            return;
        }

        dbUser = userService.setPrevBookingState(chat.getId());
        reply(absSender, new SendMessage(
                chat.getId().toString(),
                customReplyService.findCustomTextOrDefault(dbUser.getBookingState().getMessageToSend())
        ));
    }
}
