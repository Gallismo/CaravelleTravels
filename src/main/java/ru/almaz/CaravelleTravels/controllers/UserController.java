package ru.almaz.CaravelleTravels.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.almaz.CaravelleTravels.entities.User;
import ru.almaz.CaravelleTravels.services.UserService;

@Controller
@Slf4j
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/permission")
    public String permPage(Model model) {
        log.info("Permission page opened");

        model.addAttribute("user", new User());
        return "permission";
    }

    @PostMapping("/permission")
    public String givePermission(@ModelAttribute("user") User user) {
        log.info("Give permission to user " + user + " executed");

        userService.givePermissionForChatId(user);
        return "redirect:/";
    }
}
