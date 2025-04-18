package org.example.codeformater.Controller;

import org.example.codeformater.Service.UserFormatterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FormController {

    public final UserFormatterService userFormatterService;

    public FormController(UserFormatterService userFormatterService) {
        this.userFormatterService = userFormatterService;
    }

    @GetMapping("/userForm")
    public String userForm() {
        return "userForm";
    }

    @PostMapping("/codeResult")
    public String codeResult(@RequestParam String userCode, Model model) {
        model.addAttribute("userCode", userCode);
        String output = userFormatterService.makeFormatter(userCode);
        model.addAttribute("codeOutput", output);
        return "codeResult";
    }
}
