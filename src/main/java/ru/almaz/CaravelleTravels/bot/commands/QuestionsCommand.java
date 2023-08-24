package ru.almaz.CaravelleTravels.bot.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.almaz.CaravelleTravels.bot.Keyboards;
import ru.almaz.CaravelleTravels.bot.commands.abstr.MyCommand;
import ru.almaz.CaravelleTravels.config.RepliesText;
import ru.almaz.CaravelleTravels.entities.Answer;
import ru.almaz.CaravelleTravels.services.AnswerService;
import ru.almaz.CaravelleTravels.services.CustomReplyService;

import java.util.List;

@Component
@Slf4j
public class QuestionsCommand extends MyCommand {
    private final AnswerService answerService;
    private final CustomReplyService customReplyService;

    @Autowired
    public QuestionsCommand(AnswerService answerService, CustomReplyService customReplyService) {
        super("questions", "Часто задаваемые вопросы и ответы на них");
        this.answerService = answerService;
        this.customReplyService = customReplyService;
    }


    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        log.info("Questions command executed by user" + user + " by chat" + chat);

        List<Answer> answers = answerService.findAll();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat.getId());
        sendMessage.setText(customReplyService.findCustomTextOrDefault(RepliesText.questionsMessageText));
        sendMessage.setReplyMarkup(Keyboards.getQuestionsKeyboard(answers));
        reply(absSender, sendMessage);
    }
}
