package by.agat.system.controller;

import by.agat.system.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class MainController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/success")
    public String redirectByRoleUser(Authentication authentication, ModelAndView model) {
        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {

            return "redirect:/admin";
        } else if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("INSPECTOR"))) {
            return "redirect:/inspector";
        } else if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("BANK"))) {
            return "redirect:/bank";
        }
        return "login";
    }

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("bcrypt")
    @ResponseBody
    public String getbcrypt(@RequestParam() String password){
        return bCryptPasswordEncoder.encode(password);
    }




}
