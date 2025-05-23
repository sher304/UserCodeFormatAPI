package org.example.codeformater.Controller;

import com.google.googlejavaformat.java.FormatterException;
import org.example.codeformater.Service.CodeSerialization;
import org.example.codeformater.Service.UserFormatterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/codeFormater")
public class FormController {

    public final UserFormatterService userFormatterService;
    public final CodeSerialization codeSerialization;

    public FormController(UserFormatterService userFormatterService, CodeSerialization codeSerialization) {
        this.userFormatterService = userFormatterService;
        this.codeSerialization = codeSerialization;
    }

    @GetMapping("/userForm")
    public String userForm() {
        return "userForm";
    }

    @PostMapping("/codeResult")
    public String codeResult(@RequestParam String userCode, @RequestParam String expiry,
                             @RequestParam String codeId, Model model) {
        model.addAttribute("userCode", userCode);
        String output = "";
        try {
            output = userFormatterService.makeFormatter(userCode);
            model.addAttribute("codeOutput", output);
            long expirySeconds = Long.parseLong(expiry);
            codeSerialization.saveUserCode(codeId, output, expirySeconds);
            model.addAttribute("expiry", expiry);
            model.addAttribute("codeId", codeId);
        } catch (FormatterException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "codeResult";
    }

    @GetMapping("/savedCode")
    public String savedCode(@RequestParam String codeID, Model model) {
        String codeIDOutput = codeSerialization.loadFormattedCode(codeID);
        model.addAttribute("codeID", codeIDOutput);
        return "savedCode";
    }
}
