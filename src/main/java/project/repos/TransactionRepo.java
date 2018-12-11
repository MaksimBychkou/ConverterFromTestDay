package project.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entities.Transaction;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {

    List<Transaction> findByLoginOrderByDateDesc(String login);
    List<Transaction> findByOperationAndLoginOrderByDateDesc(String operation, String login);
    List<Transaction> findByOperationOrderByDateDesc(String operation);
    List<Transaction> findByIdGreaterThanOrderByDateDesc(Integer id);
}

