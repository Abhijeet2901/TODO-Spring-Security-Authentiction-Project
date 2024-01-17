package com.example.notes.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(value = "user")
@Data
public class User {

    @Transient
    public static final String SEQUENCE_NAME = "user_sequence";

    @Id
    private Long id;

    private String firstName;

    private String lastName;

    @Indexed(unique = true)
    private String email;

    private String password;

    private Set<Role> roles;
}
