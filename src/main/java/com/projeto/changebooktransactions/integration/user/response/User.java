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
public class User {

    @JsonProperty("user_name")
    private String userName;

    private String cpf;

    private String city;

    private String email;

    private String phone;

}
