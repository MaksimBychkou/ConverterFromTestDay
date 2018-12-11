package project.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entities.Usr;

import java.util.List;

public interface UsrRepo extends JpaRepository<Usr, Long>{
    Usr findByLogin(String login);
}
