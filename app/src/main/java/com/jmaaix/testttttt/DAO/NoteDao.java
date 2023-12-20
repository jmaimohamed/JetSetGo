package com.jmaaix.testttttt.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jmaaix.testttttt.entities.Note;

import java.util.List;

@Dao
public interface NoteDao {


    @Insert
    void addNote(Note note);

    @Query("SELECT * FROM note WHERE title = :title")
    Note getNoteByTitle(String title);

    @Query("SELECT * FROM note WHERE id = :noteId")
    Note getNoteById(long noteId);

    @Query("SELECT * FROM note")
    List<Note> getAllNotes();

    @Update
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Query("DELETE FROM note WHERE id = :noteId")
    void deleteNoteById(long noteId);
}


