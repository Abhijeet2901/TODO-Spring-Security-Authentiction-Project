package com.example.notes.controller;

import com.example.notes.model.Note;
import com.example.notes.repository.NoteRepository;
import com.example.notes.service.SequenceGeneratorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @GetMapping("/")
    public String test(){
        return "Hello World!!";
    }

    @GetMapping("/get")
    public Note getNote(@RequestParam Long id){
        return noteRepository.findById(id).get();
    }

    @PostMapping("/create")
    public Note createNote(@RequestBody Note note){
        note.setId(sequenceGeneratorService.getNextSequenceId("note-todo_sequence"));
        return noteRepository.save(note);
    }

    @PutMapping("/update")
    public Note updateNote(@RequestBody Note note){
        Note dbNote = noteRepository.findById(note.getId()).get();
        BeanUtils.copyProperties(note,dbNote);
        return noteRepository.save(dbNote);
    }

    @DeleteMapping("/delete")
    public void deleteNote(@RequestParam Long id){
        noteRepository.deleteById(id);
    }
}
