package project.services.impl;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.entities.Transaction;
import project.entities.Usr;
import project.repos.TransactionRepo;
import project.repos.UsrRepo;
import project.services.StoryService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class StoryServiceImpl implements StoryService {
    @Autowired
    UsrRepo usrRepo;

    @Autowired
    TransactionRepo transactionRepo;

    @Override
    public String storyPage(Map<String, Object> model, String filter, String param) throws ServiceException {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                String login = authentication.getName();
                model.put("login", login);
                String role = authentication.getAuthorities().toString().replaceAll("\\[", "").replaceAll("\\]", "");
                model.put("role", role);
                model.put("usrList", Collections.emptyList());

                Usr usr = usrRepo.findByLogin(login);
                if (usr != null) {
                    model.put("userName", usr.getUserName());
                }

                List<Transaction> transactions = transactionRepo.findByLoginOrderByDateDesc(login);
                if (role.equals("admin")) {
                    model.put("usrList", usrRepo.findAll());
                    if (filter.matches("increase|donation|sell|buy")) {
                        transactions = transactionRepo.findByOperationAndLoginOrderByDateDesc(filter, login);
                    } else if (filter.matches("increaseAll|donationAll|sellAll|buyAll")) {
                        transactions = transactionRepo.findByOperationOrderByDateDesc(filter.substring(0, filter.length() - 3));
                    } else if (filter.equals("actionAll")) {
                        transactions = transactionRepo.findByIdGreaterThanOrderByDateDesc(0);
                    } else if (filter.equals("user") && !param.equals("")) {
                        transactions = transactionRepo.findByLoginOrderByDateDesc(param);
                    }/*else{
                        transactions = transactionRepo.findByLoginOrderByDateDesc(login);
                    }*/
                } else {
                    if (filter.matches("increase|donation|sell|buy")) {
                        transactions = transactionRepo.findByOperationAndLoginOrderByDateDesc(filter, login);
                    }/*else{
                        transactions = transactionRepo.findByLoginOrderByDateDesc(login);
                    }*/
                }
                model.put("transactions", transactions);
            }
        } catch (ServiceException e) {
            System.out.println("System exception! Try again, please...");
            System.out.println(":: Exception :: StoryServiceImpl.java :: storyPage :: " + e.toString());
        }
        return "storyPage";
    }
}