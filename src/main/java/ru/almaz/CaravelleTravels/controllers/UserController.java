package ru.almaz.CaravelleTravels.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.almaz.CaravelleTravels.entities.User;
import ru.almaz.CaravelleTravels.services.UserService;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/permission")
    public String permPage(Model model) {
        model.addAttribute("user", new User());
        return "permission";
    }

    @PostMapping("/permission")
    public String givePermission(@ModelAttribute("user") User user) {
        userService.givePermissionForChatId(user);
        return "redirect:/";
    }
}
