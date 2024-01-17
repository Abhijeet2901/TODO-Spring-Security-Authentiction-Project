package com.example.notes.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Document(value = "user_role")
public class UserRole {

    @Transient
    public static final String SEQUENCE_NAME = "user_role_sequence";

    @Id
    private ObjectId id;

    private Long userId;
    private Set<Long> roleIds;
}
