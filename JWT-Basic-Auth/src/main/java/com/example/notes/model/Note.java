package com.example.notes.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "note-todo")
public class Note {

    @Transient
    public static final String SEQUENCE_NAME = "note-todo_sequence";

    @Id
    private Long id;

    private String title;

    private String description;
}
