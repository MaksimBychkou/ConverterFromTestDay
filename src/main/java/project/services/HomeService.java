package project.services;

import org.hibernate.service.spi.ServiceException;
import project.entities.Transaction;

import java.util.Map;

public interface HomeService {

    String homePage(Map<String,Object> model) throws ServiceException;

    String checkChange(Map<String, Object> model, String userNameNew, String loginNew, String passwordNew, String login) throws ServiceException;
    String change (String userNameNew, String loginNew, String passwordNew, String codeNew, String login) throws ServiceException;

    String checkIncrease(Map<String, Object> model, Double sum, String login, String role) throws ServiceException;
    String increase(Double sum, String login, String role) throws ServiceException;

    String checkDonate(Map<String, Object> model, Double usd, Double eth, String login, String role) throws ServiceException;
    String donate(Double usd, Double eth, String login, String role) throws ServiceException;

    String checkFirstCapital(Map<String, Object> model, Double usd, Double eth, String login) throws ServiceException;
    String firstCapital(Double usd, Double eth, String login, String role) throws ServiceException;

    String transfer(Map<String, Object> model, String operation, String type, Double value, String login, String role) throws ServiceException;

    Double converter(String operation, String type, Double value);

    void insertMistake(Transaction transaction) throws ServiceException;
}
