package project.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.entities.Usr;
import project.repos.UsrRepo;

import java.util.Map;


@Controller
public class RegistrationController {
    @Autowired
    private UsrRepo usrRepo;

    @GetMapping("/registration")
    public String registration(Map<String, Object> model){
        model.put("err","");
        model.put("message","");
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam String code, Usr usr, Map<String, Object> model){
        if(usr.getUserName().trim().isEmpty() || usr.getPassword().trim().isEmpty() || usr.getLogin().trim().isEmpty()){
            model.put("err", "");
            model.put("message", "All fields * are required.");
            return "/registration";
        }

        Usr usrFromDb = usrRepo.findByLogin(usr.getLogin());
        if(usrFromDb != null){
            model.put("err", "User exists!");
            model.put("message", "");
            return "/registration";
        }

        model.put("err", "");
        model.put("message", "");
        usr.setRole(code.equals("MaksimBychkou") ? "admin" : "user");
        usr.setActive(true);
        usrRepo.save(usr);

        return "redirect:/login";
    }
}