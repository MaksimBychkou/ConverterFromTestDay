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
import project.services.HomeService;

import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class HomeServiceImpl implements HomeService{
    @Autowired
    UsrRepo usrRepo;

    @Autowired
    TransactionRepo transactionRepo;

    @Override
    public String homePage(Map<String, Object> model) throws ServiceException {
        if(model.get("err")==null) model.put("err", "");
        if(model.get("message")==null) model.put("message", "");
        if(model.get("alert")==null) model.put("alert", "");
        if(model.get("userName")==null) model.put("userName", "");
        if(model.get("login")==null) model.put("login", "");
        if(model.get("balanceEth")==null) model.put("balanceEth", "");
        if(model.get("balanceUsd")==null) model.put("balanceUsd", "");
        if(model.get("role")==null) model.put("role", "");

        try {
            String login = model.get("login").toString();
            String role = model.get("role").toString();
            if (login.equals("") || role.equals("")) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null) {
                    login = authentication.getName();
                    model.put("login", login);
                    role = authentication.getAuthorities().toString().replaceAll("\\[", "").replaceAll("\\]", "");
                    model.put("role", role);
                }
            }

            Usr usr = usrRepo.findByLogin(login);
            if (usr != null) {
                model.put("userName", usr.getUserName());
            }

            List<Transaction> transaction = transactionRepo.findByLoginOrderByDateDesc(login);
            if (transaction.size() > 0) {
                model.put("balanceEth", transaction.get(0).getBalanceEth());
                model.put("balanceUsd", transaction.get(0).getBalanceUsd());
            }
        }catch(ServiceException e){
            System.out.println("System exception! Try again, please...");
            System.out.println(":: Exception :: HomeServiceImpl.java :: homePage :: " + e.toString());
        }
        return "homePage";
    }

    @Override
    public String checkChange(Map<String, Object> model, String userNameNew, String loginNew, String passwordNew, String login) throws ServiceException {
        model.put("alert","");
        if(userNameNew.trim().isEmpty() || loginNew.trim().isEmpty() || passwordNew.trim().isEmpty()){
            model.put("alert", "All fields * are required.");
        }
        if(!loginNew.trim().equals(login)){
            try {
                Usr usrNew = usrRepo.findByLogin(loginNew);
                if(usrNew != null){
                    model.put("alert", "This login already exists.");
                }
            }catch(ServiceException e){
                System.out.println("System exception! Try again, please...");
                System.out.println(":: Exception :: HomeServiceImpl.java :: checkChange :: " + e.toString());
            }
        }
        if(!model.get("alert").equals("")){
            model.put("message","You made a mistake when entering new information. You can try again...");
            return homePage(model);
        }else{
            return "";
        }
    }

    @Override
    public String change(String userNameNew, String loginNew, String passwordNew, String codeNew, String login) throws ServiceException {
        String roleNew = codeNew.equals("MaksimBychkou") ? "admin" : "user";

        try{
            if(!loginNew.trim().equals(login)) {
                Usr usrOld = usrRepo.findByLogin(login);
                usrOld.setActive(false);
                usrRepo.save(usrOld);

                Usr usrNew = new Usr();
                usrNew.setUserName(userNameNew);
                usrNew.setLogin(loginNew);
                usrNew.setPassword(passwordNew);
                usrNew.setRole(roleNew);
                usrNew.setActive(true);
                usrRepo.save(usrNew);
            }else{
                Usr usrOld = usrRepo.findByLogin(login);
                usrOld.setUserName(userNameNew);
                usrOld.setRole(roleNew);
                usrOld.setPassword(passwordNew);
                usrRepo.save(usrOld);
            }

            List<Transaction> transaction = transactionRepo.findByLoginOrderByDateDesc(login);
            if(transaction.size() > 0){
                Transaction transactionNew = new Transaction();
                transactionNew.setUsd(transaction.get(0).getUsd());
                transactionNew.setEth(transaction.get(0).getEth());
                transactionNew.setBalanceUsd(transaction.get(0).getBalanceUsd());
                transactionNew.setBalanceEth(transaction.get(0).getBalanceEth());
                transactionNew.setDate(new Date());
                transactionNew.setStatus("complete");
                transactionNew.setOperation("change ("+login+")");
                transactionNew.setLogin(loginNew);
                transactionNew.setRole(roleNew);
                transactionRepo.save(transactionNew);
            }
        }catch(ServiceException e){
            System.out.println("System exception! Try again, please...");
            System.out.println(":: Exception :: HomeServiceImpl.java :: change :: " + e.toString());
        }
        return "redirect:/login";
    }

    @Override
    public String checkIncrease(Map<String, Object> model, Double sum, String login, String role) throws ServiceException {
        if(sum == null || sum == 0.0){
            Transaction transaction = new Transaction();
            transaction.setOperation("increase");
            transaction.setLogin(login);
            transaction.setRole(role);
            insertMistake(transaction);

            model.put("message","Increase value must be greater than 0.0");
            return homePage(model);
        }

        try {
            List<Transaction> currentValueDb = transactionRepo.findByLoginOrderByDateDesc(login);
            if (currentValueDb.size() > 0) {
                sum += currentValueDb.get(0).getBalanceUsd();
                if (sum > 999999.0) {
                    Transaction transaction = new Transaction();
                    transaction.setOperation("increase");
                    transaction.setLogin(login);
                    transaction.setRole(role);
                    insertMistake(transaction);

                    model.put("message", "Don't be greedy. You have enough money!");
                    return homePage(model);
                }
            }
        }catch(ServiceException e){
            System.out.println("System exception! Try again, please...");
            System.out.println(":: Exception :: HomeServiceImpl.java :: checkIncrease :: " + e.toString());
        }
        return "";
    }

    @Override
    public String increase(Double sum, String login, String role) throws ServiceException {
        Double balanceUsd = sum;
        Double balanceEth = 0.0;

        try {
            List<Transaction> currentValueDb = transactionRepo.findByLoginOrderByDateDesc(login);
            if (currentValueDb.size() > 0) {
                balanceUsd += currentValueDb.get(0).getBalanceUsd();
                balanceEth = currentValueDb.get(0).getBalanceEth();
            }

            Transaction transaction = new Transaction();
            transaction.setBalanceUsd(balanceUsd);
            transaction.setBalanceEth(balanceEth);
            transaction.setUsd(sum);
            transaction.setEth(0.0);
            transaction.setDate(new Date());
            transaction.setOperation("increase");
            transaction.setStatus("complete");
            transaction.setLogin(login);
            transaction.setRole(role);
            transactionRepo.save(transaction);

        }catch(ServiceException e){
            System.out.println("System exception! Try again, please...");
            System.out.println(":: Exception :: HomeServiceImpl.java :: increase :: " + e.toString());
        }
        return "redirect:/storyPage";
    }

    @Override
    public String checkDonate(Map<String, Object> model, Double usd, Double eth, String login, String role) throws ServiceException {
        if(usd == null) usd = 0.0;
        if(eth == null) eth = 0.0;

        if(usd == 0.0 && eth == 0.0){
            Transaction transaction = new Transaction();
            transaction.setOperation("donation");
            transaction.setLogin(login);
            transaction.setRole(role);
            insertMistake(transaction);

            model.put("message","You can't donate nothing!");
            return homePage(model);
        }

        try {
            List<Transaction> currentValueDb = transactionRepo.findByLoginOrderByDateDesc(login);
            if (currentValueDb.size() > 0) {
                if ((currentValueDb.get(0).getBalanceUsd() - usd) < 0 || (currentValueDb.get(0).getBalanceEth() - eth) < 0) {
                    Transaction transaction = new Transaction();
                    transaction.setOperation("donation");
                    transaction.setLogin(login);
                    transaction.setRole(role);
                    insertMistake(transaction);

                    model.put("message", "You can't donate more money than you have!");
                    return homePage(model);
                }
            } else {
                Transaction transaction = new Transaction();
                transaction.setOperation("donation");
                transaction.setLogin(login);
                transaction.setRole(role);
                insertMistake(transaction);

                model.put("message", "You haven't got enough money...");
                return homePage(model);
            }
        }catch(ServiceException e){
            System.out.println("System exception! Try again, please...");
            System.out.println(":: Exception :: HomeServiceImpl.java :: checkDonate :: " + e.toString());
        }
        return "";
    }

    @Override
    public String donate(Double usd, Double eth, String login, String role) throws ServiceException {
        try {
            List<Transaction> currentValueDb = transactionRepo.findByLoginOrderByDateDesc(login);

            Transaction transaction = new Transaction();
            transaction.setBalanceUsd(currentValueDb.get(0).getBalanceUsd() - usd);
            transaction.setBalanceEth(currentValueDb.get(0).getBalanceEth() - eth);
            transaction.setUsd(usd * (-1));
            transaction.setEth(eth * (-1));
            transaction.setDate(new Date());
            transaction.setOperation("donation");
            transaction.setStatus("complete");
            transaction.setLogin(login);
            transaction.setRole(role);

            transactionRepo.save(transaction);
        }catch(ServiceException e){
            System.out.println("System exception! Try again, please...");
            System.out.println(":: Exception :: HomeServiceImpl.java :: donate :: " + e.toString());
        }
        return "redirect:/storyPage";
    }

    @Override
    public String transfer(Map<String, Object> model, String oper, String type, Double val, String login, String role) throws ServiceException {
        Transaction transaction = new Transaction();
        if(val==null || val == 0.0){
            transaction.setOperation("transfer");
            transaction.setLogin(login);
            transaction.setRole(role);
            insertMistake(transaction);

            model.put("message","Transfer value can't be zero!");
            return homePage(model);
        }
        if(!oper.matches("1|2") || !type.matches("1|2")){
            model.put("message","System exception. Transfer can't be succesfull! Try again, please...");
            return homePage(model);
        }

        try {
            List<Transaction> currentValueDb = transactionRepo.findByLoginOrderByDateDesc(login);
            if (currentValueDb.size() > 0) {
                Double convertVal = converter(oper, type, val);

                transaction.setDate(new Date());
                transaction.setLogin(login);
                transaction.setRole(role);

                if (oper.equals("1")) {
                    transaction.setOperation("buy");
                    if (type.equals("1")) { //+usd, -eth(convertval)
                        transaction.setEth(convertVal);
                        transaction.setUsd(val);
                        if ((currentValueDb.get(0).getBalanceEth() + convertVal) >= 0) {
                            transaction.setBalanceEth(currentValueDb.get(0).getBalanceEth() + convertVal);
                            transaction.setBalanceUsd(currentValueDb.get(0).getBalanceUsd() + val);
                        } else {
                            transaction.setStatus("error");
                            transaction.setBalanceEth(currentValueDb.get(0).getBalanceEth());
                            transaction.setBalanceUsd(currentValueDb.get(0).getBalanceUsd());
                            transactionRepo.save(transaction);

                            model.put("err", "You have no ETH for the operation!");
                            return homePage(model);
                        }
                    } else { //-usd(convertval), +eth
                        transaction.setEth(val);
                        transaction.setUsd(convertVal);
                        if ((currentValueDb.get(0).getBalanceUsd() + convertVal) >= 0) {
                            transaction.setBalanceEth(currentValueDb.get(0).getBalanceEth() + val);
                            transaction.setBalanceUsd(currentValueDb.get(0).getBalanceUsd() + convertVal);
                        } else {
                            transaction.setStatus("error");
                            transaction.setBalanceEth(currentValueDb.get(0).getBalanceEth());
                            transaction.setBalanceUsd(currentValueDb.get(0).getBalanceUsd());
                            transactionRepo.save(transaction);

                            model.put("message", "You have no USD for the operation! You can increase enough money...");
                            return homePage(model);
                        }
                    }
                } else {
                    transaction.setOperation("sell");
                    if (type.equals("1")) { //-usd, +eth(convertval)
                        transaction.setEth(convertVal);
                        transaction.setUsd(val * (-1));
                        if ((currentValueDb.get(0).getBalanceUsd() - val) >= 0) {
                            transaction.setBalanceEth(currentValueDb.get(0).getBalanceEth() + convertVal);
                            transaction.setBalanceUsd(currentValueDb.get(0).getBalanceUsd() - val);
                        } else {
                            transaction.setStatus("error");
                            transaction.setBalanceEth(currentValueDb.get(0).getBalanceEth());
                            transaction.setBalanceUsd(currentValueDb.get(0).getBalanceUsd());
                            transactionRepo.save(transaction);

                            model.put("message", "You have no USD for the operation! You can increase enough money...");
                            return homePage(model);
                        }
                    } else { //+usd(convertval), -eth
                        transaction.setEth(val * (-1));
                        transaction.setUsd(convertVal);
                        if ((currentValueDb.get(0).getBalanceEth() - val) >= 0) {
                            transaction.setBalanceEth(currentValueDb.get(0).getBalanceEth() - val);
                            transaction.setBalanceUsd(currentValueDb.get(0).getBalanceUsd() + convertVal);
                        } else {
                            transaction.setStatus("error");
                            transaction.setBalanceEth(currentValueDb.get(0).getBalanceEth());
                            transaction.setBalanceUsd(currentValueDb.get(0).getBalanceUsd());
                            transactionRepo.save(transaction);

                            model.put("err", "You have no ETH for the operation!");
                            return homePage(model);
                        }
                    }
                }

                transaction.setStatus("complete");
                transactionRepo.save(transaction);

                //return "redirect:/storyPage";
            } else {
                transaction.setOperation("transfer");
                transaction.setLogin(login);
                transaction.setRole(role);
                insertMistake(transaction);

                model.put("err", "You have no money! Create start capital or increase money, please...");
                return homePage(model);
            }
        }catch(ServiceException e){
            System.out.println("System exception! Try again, please...");
            System.out.println(":: Exception :: HomeServiceImpl.java :: transfer :: " + e.toString());
        }
        return "redirect:/storyPage";
    }

    @Override
    public String checkFirstCapital(Map<String, Object> model, Double usd, Double eth, String login) throws ServiceException {
        if(usd == null || usd == 0.0 || eth == null || eth == 0.0 ){
            model.put("message","All fields * are required.");
            return homePage(model);
        }

        if(eth > 2){
            model.put("err","Max value ETH = 2.000 or 2");
            return homePage(model);
        }

        try {
            List<Transaction> currentValueDb = transactionRepo.findByLoginOrderByDateDesc(login);
            if (currentValueDb.size() > 0) {
                model.put("err", "You have money in the account!");
                return homePage(model);
            }
        }catch(ServiceException e){
            System.out.println("System exception! Try again, please...");
            System.out.println(":: Exception :: HomeServiceImpl.java :: checkFirstCapital :: " + e.toString());
        }
        return "";
    }

    @Override
    public String firstCapital(Double usd, Double eth, String login, String role) throws ServiceException {
        try {
            Transaction transaction = new Transaction();
            transaction.setOperation("start capital");
            transaction.setStatus("complete");
            transaction.setDate(new Date());
            transaction.setBalanceUsd(usd);
            transaction.setBalanceEth(eth);
            transaction.setEth(eth);
            transaction.setUsd(usd);
            transaction.setLogin(login);
            transaction.setRole(role);
            transactionRepo.save(transaction);
        }catch(ServiceException e){
            System.out.println("System exception! Try again, please...");
            System.out.println(":: Exception :: HomeServiceImpl.java :: firstCapital :: " + e.toString());
        }
        return "redirect:/storyPage";
    }

    @Override
    public Double converter(String operation, String type, Double value) {
        Double res = 1.0;
        if(operation.equals("1")) res=res*(-1);
        if(type.equals("1")) res=res/(5432.1);
        if(type.equals("2")) res=res*(5432.1);

        return (Math.round(value*res*100000.0)/100000.0);
    }

    @Override
    public void insertMistake(Transaction transaction) throws ServiceException {
        try {
            List<Transaction> lastValueDb = transactionRepo.findByLoginOrderByDateDesc(transaction.getLogin());
            if (lastValueDb.size() > 0) {
                transaction.setBalanceUsd(lastValueDb.get(0).getBalanceUsd());
                transaction.setBalanceEth(lastValueDb.get(0).getBalanceEth());
            } else {
                transaction.setBalanceUsd(0.0);
                transaction.setBalanceEth(0.0);
            }
            transaction.setEth(0.0);
            transaction.setUsd(0.0);
            transaction.setDate(new Date());
            transaction.setStatus("wrong input");

            transactionRepo.save(transaction);
        }catch(ServiceException e){
            System.out.println("System exception! Try again, please...");
            System.out.println(":: Exception :: HomeServiceImpl.java :: insertMistake :: " + e.toString());
        }
    }
}