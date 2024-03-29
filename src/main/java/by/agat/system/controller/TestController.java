package by.agat.system.controller;

import by.agat.system.domain.Document;
import by.agat.system.domain.DocumentStatus;
import by.agat.system.domain.User;
import by.agat.system.repository.DocumentRepository;
import by.agat.system.repository.DocumentStatusRepository;
import by.agat.system.services.UserService;
import by.agat.system.utility.FileWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserService userService;

    @Autowired
    private DocumentRepository documentRepository;

    @GetMapping("/users")
    private @ResponseBody
    List<User> getAllUsers() {
        return userService.getAllUser();
    }

    @GetMapping("/dir")
    private @ResponseBody
    List<DocumentStatus> getDir() {

        return null;
    }


}
