package com.projeto.changebooktransactions.facade;

import com.projeto.changebooktransactions.domain.StatusTransaction;
import com.projeto.changebooktransactions.domain.Transaction;
import com.projeto.changebooktransactions.domain.TransactionRequest;
import com.projeto.changebooktransactions.integration.book.client.BookClient;
import com.projeto.changebooktransactions.integration.book.response.Book;
import com.projeto.changebooktransactions.integration.user.client.UserClient;
import com.projeto.changebooktransactions.integration.user.response.User;
import com.projeto.changebooktransactions.service.SequenceServiceGenerator;
import com.projeto.changebooktransactions.service.TransactionService;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionFacade {
    private TransactionService transactionService;

    @Autowired
    private BookClient bookClient;

    @Autowired
    private UserClient userClient;

    @Autowired
    private SequenceServiceGenerator sequenceServiceGenerator;


    @Autowired
    public TransactionFacade(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public void createTransaction(TransactionRequest transactionRequest,
                                  String Authorization) throws IllegalArgumentException {
        User user = getUserByToken(Authorization);
        Book bookPartner = bookClient.getBookById(transactionRequest.getBookPartnerId(), Authorization);
        if(!(transactionRequest.getBookUserId() == null)){
            Book bookUser = bookClient.getBookById(transactionRequest.getBookUserId(), Authorization);
            Transaction transaction = transactionRequest.toTradeTransaction(bookPartner, bookUser);
            transactionService.createTransaction(transaction);
        } else{
            Transaction transaction = transactionRequest.toSellTransaction(bookPartner, user);
            transaction.setId(sequenceServiceGenerator.generateSequence(Transaction.SEQUENCE_NAME));
            transactionService.createTransaction(transaction);
        }

    }

    public List<Transaction> getUserIncompleteTransactions(String expand,String Authorization){
        return getTransactions(expand,Authorization).stream()
                .filter(t -> t.getStatusTransaction() == StatusTransaction.PENDING).collect(Collectors.toList());
    }

    public List<Transaction> getUserCompleteTransactions(String expand,String Authorization){
        return getTransactions(expand,Authorization).stream().
                filter(t -> t.getStatusTransaction() != StatusTransaction.PENDING).collect(Collectors.toList());
    }

    private List<Transaction> getTransactions(String expand, String Authorization) {
        final User user = userClient.getUserByToken(Authorization);

        if (!transactionService.existsByOwner(user))
            throw new InternalError("O usuário não possui transações ativas.");
        if(!expand.equalsIgnoreCase("requestor") && !expand.equalsIgnoreCase("user"))
            throw new InternalError("Invalid data.");
        return transactionService.getUserTransaction(expand,user);
    }

    public void updateTransaction(String transactionId, String token) throws IllegalArgumentException {
        if (transactionId != null)
            transactionService.updateTransaction(transactionId,token);
        else
            throw new IllegalArgumentException("Invalid data.");
    }

    private User getUserByToken(String authorization){
        return userClient.getUserByToken(authorization);
    }

    public void cancelTransaction(String transactionId, String Authorization) {
        final User user = userClient.getUserByToken(Authorization);
        transactionService.cancelTransactionById(user, transactionId);
    }
}
