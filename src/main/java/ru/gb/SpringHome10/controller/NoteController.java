package ru.gb.SpringHome10.controller;

import ru.gb.SpringHome10.model.Note;
import ru.gb.SpringHome10.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService service;

    /**
     * Перехват команды на получения списка всех заметок
     * @return список заметок и код ответа 200
     */
    @GetMapping
    public ResponseEntity<List<Note>> getAll() {
        return new ResponseEntity<>(service.getAllNotes(), HttpStatus.OK);
    }

    /**
     * Перехват команды на создание заметки
     * @param note
     * @return созданную заметку и код ответа 201
     */
    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        return new ResponseEntity<>(service.createNote(note), HttpStatus.CREATED);
    }

    /**
     * Перехват команды на вывод заметки по id
     * @param id
     * @return заметку с заданным id и код ответа 200 либо код ответа 400 и новую пустую заметку, если нет заметки с заданным id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNote(@PathVariable("id") Long id) {
        Note noteById;
        try {
            noteById = service.getNoteById(id);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Note());
        }
        return new ResponseEntity<>(noteById, HttpStatus.OK);
    }

    /**
     * Перехват команды на обновление заметки с поиском нужной по id
     * @param id
     * @param note
     * @return отредактированную заметку и код ответа 200
     */
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable("id") Long id, @RequestBody Note note) {
        return new ResponseEntity<>(service.updateNote(id, note), HttpStatus.OK);
    }

    /**
     * Перехват команды на удаление заметки по ее id
     * @param id
     * @return код ответа 200
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable("id") Long id){
        service.deleteNote(id);
        return ResponseEntity.ok().build();
    }
}
