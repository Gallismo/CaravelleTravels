package ru.almaz.CaravelleTravels.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.almaz.CaravelleTravels.entities.Answer;
import ru.almaz.CaravelleTravels.services.AnswerService;

import java.util.List;

@Controller
@RequestMapping("/answers")
@Slf4j
public class AnswersController {

    private final AnswerService answerService;

    @Autowired
    public AnswersController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping("/all")
    public String showAll(Model model) {
        log.info("Answers show all page opened");


        List<Answer> answers = answerService.findAll();
        model.addAttribute("answers", answers);
        return "answers/all";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        log.info("Delete answer with id " + id + " executed");

        answerService.delete(id);
        return "redirect:/answers/all";
    }

    @GetMapping("/new")
    public String newAnswer(Model model) {
        log.info("New answer page opened");

        model.addAttribute("newAnswer", new Answer());
        return "answers/new";
    }

    @PostMapping("/new")
    public String saveNewAnswer(@ModelAttribute("newAnswer") Answer answer) {
        log.info("New answer creating executed");

        answerService.save(answer);
        return "redirect:/answers/all";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") long id) {
        log.info("Edit answer with id " + id + " page opened");

        model.addAttribute("updatedAnswer", answerService.getById(id));
        return "answers/edit";
    }

    @PatchMapping("/{id}")
    public String updateAnswer(@ModelAttribute("updatedAnswer") Answer answer, @PathVariable("id") long id) {
        log.info("Edit answer with id " + id + " edited answer " + answer);

        answerService.update(id, answer);
        System.out.println(answer);
        return "redirect:/answers/all";
    }
}
