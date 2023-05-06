package pl.kurs.java.AllegroAppl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kurs.java.AllegroAppl.model.dto.UserDTO;
import pl.kurs.java.AllegroAppl.service.UserService;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/allegro")
public class UserController {
    private final UserService userService;

    @GetMapping("/createUser")
    public String getUserData(Model model) {
        model.addAttribute("userForm", new UserDTO());
        return "createUser";
    }

    @PostMapping("/userCreated")
    public String createUser(@ModelAttribute UserDTO userDTO, Model model) {
        userService.createUser(userDTO);
        String result = "User created";
        model.addAttribute("result", result);
        return "result";
    }



}
