package sk.tuke.gamestudio.server.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.service.UserException;
import sk.tuke.gamestudio.service.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @GetMapping
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping
    public String register(@RequestParam String email,
                           @RequestParam String username,
                           @RequestParam String password,
                           Model model) {
        try {
            userService.register(email, username, password);
            model.addAttribute("message", "✅ Check your email — activation link has been sent!");
            return "register";
        } catch (UserException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code, Model model) {
        Optional<User> user = userService.findByActivationCode(code);
        if (user.isPresent()) {
            user.get().setActivationCode(null);
            userService.save(user.get());
            model.addAttribute("message", "✅ Your account is now activated. You can log in.");
            return "login";
        } else {
            model.addAttribute("error", "❌ Invalid or expired activation link.");
            return "register";
        }
    }
}










