package com.projeto.changebooktransactions.integration.user.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class User {

    @JsonProperty("user_name")
    private String userName;

    @Id
    private String cpf;

    private String city;

    private String email;

    private String phone;

}
