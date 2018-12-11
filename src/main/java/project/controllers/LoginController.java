package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.repos.UsrRepo;

import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private UsrRepo usrRepo;

    @GetMapping("/")
    public String start(Map<String, Object> model){
        model.put("err","");
        model.put("message","");
        return "login";
    }

    @GetMapping("/login")
    public String login(Map<String, Object> model){
        model.put("err","");
        model.put("message","");
        return "login";
    }

    @GetMapping("/login/error")
    public String loginError(Map<String, Object> model){
        model.put("err","Such a user does not exists! Pay attention...");
        model.put("message","");
        return "login";
    }
}
