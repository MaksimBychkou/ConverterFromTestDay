package project.controllers;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.entities.Transaction;
import project.entities.Usr;
import project.repos.TransactionRepo;
import project.repos.UsrRepo;
import project.services.StoryService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class StoryController {
    @Autowired
    private StoryService storyService;

    @GetMapping("/storyPage")
    private String storyPage(Map<String, Object> model, @RequestParam(name = "filter", required = false, defaultValue = "") String filter,
                             @RequestParam(name = "param", required = false, defaultValue = "") String param) throws ServiceException{

        return storyService.storyPage(model,filter,param);
    }
}
