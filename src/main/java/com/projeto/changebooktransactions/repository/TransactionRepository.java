package com.projeto.changebooktransactions.repository;

import com.projeto.changebooktransactions.domain.Transaction;
import com.projeto.changebooktransactions.integration.user.response.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {

    List<Transaction> getByNewOwner(User newOwner);

    boolean existsByNewOwner(User newOwner);

    List<Transaction> getByOldOwner(User user);

    Boolean existsByOldOwner(User oldOwner);
}
