package project.controllers;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.services.HomeService;

import java.util.*;


@Controller
public class HomeController {
    @Autowired
    private HomeService homeService;

    @GetMapping("/homePage")
    public String getHomePage(Map<String, Object> model) throws ServiceException {
        return homeService.homePage(model);
    }

    @PostMapping("/change")
    private String change(@RequestParam(name = "login") String login,
                             @RequestParam(name = "userNameNew") String userNameNew,
                             @RequestParam(name = "codeNew") String codeNew,
                             @RequestParam(name = "loginNew") String loginNew,
                             @RequestParam(name = "passwordNew") String passwordNew,
                             Map<String, Object> model) throws ServiceException {

        String res = homeService.checkChange(model, userNameNew, loginNew, passwordNew, login);
        return (StringUtils.EMPTY).equals(res) ? homeService.change(userNameNew, loginNew, passwordNew, codeNew, login) : res;
    }

    @PostMapping("/increase")
    private String getIncrease(@RequestParam(name = "sum") Double sum,
                               @RequestParam(name = "login") String login,
                               @RequestParam(name = "role") String role,
                               Map<String, Object> model) {

        String res = homeService.checkIncrease(model, sum, login, role);
        return (StringUtils.EMPTY).equals(res) ? homeService.increase(sum, login, role) : res;
    }

    @PostMapping("/donate")
    private String donate(@RequestParam(name = "donateUsd") Double usd,
                          @RequestParam(name = "donateEth") Double eth,
                          @RequestParam(name = "login") String login,
                          @RequestParam(name = "role") String role,
                          Map<String, Object> model) {

        String res = homeService.checkDonate(model, usd, eth, login, role);
        return (StringUtils.EMPTY).equals(res) ? homeService.donate(usd, eth, login, role) : res;
    }

    @PostMapping("/first")
    private String firstCapital(@RequestParam(name = "usd") Double usd,
                                @RequestParam(name = "eth") Double eth,
                                @RequestParam(name = "login") String login,
                                @RequestParam(name = "role") String role,
                                Map<String, Object> model) {

        String res = homeService.checkFirstCapital(model, usd, eth, login);
        return  (StringUtils.EMPTY).equals(res) ? homeService.firstCapital(usd, eth, login, role) : res;
    }

    @PostMapping("/transfer")
    private String transfer(@RequestParam(name = "transoper") String oper,
                            @RequestParam(name = "transtype") String type,
                            @RequestParam(name = "transval") Double val,
                            @RequestParam(name = "login") String login,
                            @RequestParam(name = "role") String role,
                            Map<String, Object> model) {

        return homeService.transfer(model, oper, type, val, login, role);
    }
}
