package by.agat.system.controller;

import by.agat.system.domain.Document;
import by.agat.system.domain.User;
import by.agat.system.services.UserService;
import by.agat.system.utility.FileWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/files")
public class FileController {

    @Autowired
    private UserService userService;

    /*@GetMapping(value = "/all")
    private @ResponseBody
    List<Document> getAllDocuments(Authentication authentication) {
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        List<Document> documents = new ArrayList<>();
        FileWorker fileWorker = new FileWorker(user.getDirectory());
        documents = fileWorker.getAllDocuments();
        return documents;
    }*/


}
