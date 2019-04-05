package by.agat.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inspector")
public class InspectorController {

    @GetMapping({"/", ""})
    public String inspectorPage() {
        return "inspector/index";
    }
}
