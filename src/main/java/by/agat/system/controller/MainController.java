package by.agat.system.controller;

import by.agat.system.domain.User;
import org.apache.log4j.Logger;
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



@Controller
public class MainController {

    private static final Logger logger = Logger.getLogger(MainController.class);

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/success")
    public String redirectByRoleUser(Authentication authentication, ModelAndView model) {
        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            logger.info("USER connected with ADMIN role");
            return "redirect:/admin";
        } else if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("INSPECTOR"))) {
            logger.info("USER connected with INSPECTOR role");
            return "redirect:/inspector";
        } else if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("BANK"))) {
            logger.info("USER connected with BANK role");
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
