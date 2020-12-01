package com.projeto.changebooktransactions.integration.book.response;

import com.projeto.changebooktransactions.config.Messages;
import com.projeto.changebooktransactions.integration.user.response.User;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
@ToString
public class Book {

    @Id
    private String id;

    private String name;

    private String authorName;

    private String editorName;

    private String description;

    private String category;

    private Boolean isForTrade;

    private String tradeDescription;

    private Boolean isForSell;

    @NotNull(message = Messages.IMAGE_IS_REQUIRED)
    private String image;

    @DBRef(db = "user")
    private User user;
}
