package ru.almaz.CaravelleTravels.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.almaz.CaravelleTravels.entities.UpdatableContent;
import ru.almaz.CaravelleTravels.services.UpdatableContentService;

import java.util.List;

@Controller
@RequestMapping("/information")
public class InformationController {
    private final UpdatableContentService updatableContentService;

    @Autowired
    public InformationController(UpdatableContentService updatableContentService) {
        this.updatableContentService = updatableContentService;
    }

    @GetMapping("/all")
    public String all(Model model) {
        List<UpdatableContent> informations = updatableContentService.findAll();
        model.addAttribute("informations", informations);
        return "information/all";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        updatableContentService.deleteById(id);
        return "redirect:/information/all";
    }

    @GetMapping("/new")
    public String newInformation(Model model) {
        model.addAttribute("newInformation", new UpdatableContent());
        return "information/new";
    }

    @PostMapping("/new")
    public String save(@ModelAttribute("newInformation") UpdatableContent newInformation) {
        updatableContentService.save(newInformation);
        return "redirect:/information/all";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("updatedInformation", updatableContentService.findById(id));
        return "information/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("updatedInformation") UpdatableContent updatedInformation, @PathVariable("id") long id) {
        updatableContentService.update(id, updatedInformation);
        return "redirect:/information/all";
    }
}
