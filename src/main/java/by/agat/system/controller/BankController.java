package by.agat.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bank")
public class BankController {

    @GetMapping({"/", ""})
    public String bankPage() {
        return "bank/index";
    }
}
