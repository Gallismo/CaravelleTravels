package ru.almaz.CaravelleTravels.bot.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.almaz.CaravelleTravels.bot.Keyboards;
import ru.almaz.CaravelleTravels.bot.commands.abstr.MyCommand;
import ru.almaz.CaravelleTravels.config.TextConfig;
import ru.almaz.CaravelleTravels.entities.Answer;
import ru.almaz.CaravelleTravels.services.AnswerService;

import java.util.List;

@Component
public class QuestionsCommand extends MyCommand {
    private final AnswerService answerService;

    @Autowired
    public QuestionsCommand(AnswerService answerService) {
        super("questions", "Часто задаваемые вопросы и ответы на них");
        this.answerService = answerService;
    }


    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        List<Answer> answers = answerService.findAll();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat.getId());
        sendMessage.setText(TextConfig.questionsMessageText);
        sendMessage.setReplyMarkup(Keyboards.getQuestionsKeyboard(answers));
        reply(absSender, sendMessage);
    }
}
