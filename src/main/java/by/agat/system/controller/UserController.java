package by.agat.system.controller;

import by.agat.system.domain.User;
import by.agat.system.domain.UserDTO;
import by.agat.system.domain.UserSaveModel;
import by.agat.system.repository.UserRepository;
import by.agat.system.services.UserService;
import by.agat.system.utility.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping({"/", ""})
    public @ResponseBody
    List<UserDTO> getAllUsers() {
        List<UserDTO> userDTOs = new ArrayList<>();
        List<User> users = userService.getAllUser();
        for (User user:users) {
            UserDTO userDTO = Converter.convertFromUserToDTO(user);
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }


}
