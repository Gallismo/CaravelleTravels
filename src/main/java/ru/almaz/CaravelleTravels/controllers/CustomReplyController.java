package ru.almaz.CaravelleTravels.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.almaz.CaravelleTravels.entities.CustomBotReply;
import ru.almaz.CaravelleTravels.services.CustomReplyService;

import java.util.List;

@Controller
@RequestMapping("/replies")
public class CustomReplyController {
    private final CustomReplyService customReplyService;

    @Autowired
    public CustomReplyController(CustomReplyService customReplyService) {
        this.customReplyService = customReplyService;
    }

    @GetMapping("/all")
    public String allPage(Model model) {
        List<CustomBotReply> replies = customReplyService.findAll();
        model.addAttribute("replies", replies);
        return "replies/all";
    }

    @GetMapping("/edit/{id}")
    public String editPage(Model model, @PathVariable("id") long id) {
        model.addAttribute("reply", customReplyService.findById(id));
        return "replies/edit";
    }

    @PatchMapping("/{id}")
    public String edit(@ModelAttribute("reply") CustomBotReply customBotReply, @PathVariable("id") long id) {
        customReplyService.update(id, customBotReply);
        return "redirect:/replies/all";
    }
}
