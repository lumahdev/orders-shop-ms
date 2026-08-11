package dev.lumah.orders_ms.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private Address address;
    private LocalDate creationDate;
    private Boolean active;
    private Boolean emailValidated;
}
