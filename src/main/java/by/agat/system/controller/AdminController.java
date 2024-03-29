package by.agat.system.controller;

import by.agat.system.domain.User;
import by.agat.system.domain.UserSaveModel;
import by.agat.system.services.UserService;
import by.agat.system.utility.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping({"/", ""})
    public ModelAndView adminPage(ModelAndView model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        model.addObject("user", user);
        model.setViewName("admin/index");
        return model;
    }

    @GetMapping("/users")
    public ModelAndView usersPage(ModelAndView model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        model.addObject("user", user);
        model.addObject("userSaveModel", new UserSaveModel());
        model.setViewName("admin/users");
        return model;
    }


    @PostMapping("/users/save")
    public void saveUser(@ModelAttribute UserSaveModel userSaveModel) {
        userService.save(Converter.convertFromUserSaveModelToUser(userSaveModel));
    }
}
