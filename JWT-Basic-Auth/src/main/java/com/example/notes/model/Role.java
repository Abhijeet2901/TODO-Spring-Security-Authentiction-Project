package com.example.notes.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Document(value = "role")
@Data
public class Role implements GrantedAuthority {

    @Transient
    public static final String SEQUENCE_NAME = "role_sequence";

    @Id
    private Long id;

    private String roleName;

    @Override
    public String getAuthority() {
        return this.roleName;
    }
}
