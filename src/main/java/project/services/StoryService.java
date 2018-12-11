package project.services;

import org.hibernate.service.spi.ServiceException;

import java.util.Map;

public interface StoryService {

    String storyPage(Map<String, Object> model, String filter, String param) throws ServiceException;
}
