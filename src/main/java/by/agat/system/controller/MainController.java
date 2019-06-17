package by.agat.system.controller;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
            logger.info("USER login with ADMIN role");
            return "redirect:/admin";
        } else if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("INSPECTOR"))) {
            logger.info("USER login with INSPECTOR role");
            return "redirect:/inspector";
        } else if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("BANK"))) {
            logger.info("USER login with BANK role");
            return "redirect:/bank";
        }
        return "login";
    }
}
