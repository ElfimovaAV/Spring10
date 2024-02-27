package ru.gb.SpringHome10.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.gb.SpringHome10.model.Note;
import ru.gb.SpringHome10.repository.NoteRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class NoteServiceIntegrationTest {
    @MockBean
    private NoteRepository noteRepository;
    @Autowired
    private NoteService noteService;

    /**
     * Позитивный тест
     */
    @Test
    @DisplayName("Test for creating a note")
    public void createNoteTest() {
        //Arrange
        Note firstNote = new Note();
        firstNote.setId(1L);
        firstNote.setTitle("Первая заметка");
        firstNote.setDescription("Содержание первой заметки");
        firstNote.setCreateTime(LocalDateTime.now());
        when(noteRepository.save(firstNote)).thenReturn(firstNote);


        //Acts
        Note createdNote = noteService.createNote(firstNote);

        //Assert
        verify(noteRepository).save(firstNote);
        assertThat(createdNote.getId()).isEqualTo(1L);
        assertThat(createdNote.getDescription()).isEqualTo("Содержание первой заметки");
    }


}