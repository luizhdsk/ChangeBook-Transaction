package com.projeto.changebooktransactions.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.projeto.changebooktransactions.integration.book.response.Book;
import com.projeto.changebooktransactions.integration.user.response.User;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
@ToString
public class Transaction {

    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private String id;

    @DBRef(db = "book")
    private Book bookPartner;

    @DBRef(db = "book")
    private Book bookUser;

    @DBRef(db = "user")
    private User oldOwner;

    @DBRef(db = "user")
    private User newOwner;

    @JsonProperty("transaction_type")
    private TransactionType transactionType;

    private BigDecimal price;

    @JsonProperty("status_transaction")
    private StatusTransaction statusTransaction;

    @JsonProperty("end_date")
    private LocalDate endDate;
}
