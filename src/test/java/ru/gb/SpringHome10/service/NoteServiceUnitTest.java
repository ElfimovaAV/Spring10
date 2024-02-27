package ru.gb.SpringHome10.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gb.SpringHome10.model.Note;
import ru.gb.SpringHome10.repository.NoteRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceUnitTest {
    @Mock
    private NoteRepository noteRepository;
    @InjectMocks
    private NoteService noteService;

    /**
     * Позитивный тест
     */
    @Test
    @DisplayName("Test for getting a list of all notes")
    public void getAllNotesTest() {
        //Arrange
        Note firstNote = new Note();
        firstNote.setId(1L);
        firstNote.setTitle("Первая заметка");
        firstNote.setDescription("Содержание первой заметки");
        firstNote.setCreateTime(LocalDateTime.now());

        Note secondNote = new Note();
        secondNote.setId(2L);
        secondNote.setTitle("Вторая заметка");
        secondNote.setDescription("Содержание второй заметки");
        secondNote.setCreateTime(LocalDateTime.now());

        List<Note> notes = new ArrayList<>();
        notes.add(firstNote);
        notes.add(secondNote);
        when(noteRepository.findAll())
                .thenReturn(notes);
        //Acts
        noteService.getAllNotes();

        //Assert
        verify(noteRepository).findAll();
        assertEquals(2,
                noteService.getAllNotes().size());
        assertThat(notes.get(0).getId()).isEqualTo(1L);
        assertThat(notes.get(1).getTitle()).isEqualTo("Вторая заметка");
    }
    /**
     * Позитивный тест на выбрасывание исключения
     */
    @Test
    @DisplayName("Test for updating the note by id")
    public void updateNotesTest() {
        //Arrange
        Note firstNote = new Note();
        firstNote.setId(1L);
        firstNote.setTitle("Первая заметка");
        firstNote.setDescription("Содержание первой заметки");
        firstNote.setCreateTime(LocalDateTime.now());

        given(noteRepository.findById(1L))
                .willReturn(Optional.empty());

        //Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> noteService.updateNote(1L, firstNote)
        );

        verify(noteRepository, never())
                .save(any());
    }

}