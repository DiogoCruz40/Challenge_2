package pt.cm.challenge_2.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.DeleteTable;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import pt.cm.challenge_2.database.entities.Note;

@Dao
public interface NotesDAO {
    @Query("SELECT * FROM notes")
    List<Note> getAll();

    @Query("SELECT * FROM notes WHERE id_note IN (:userIds)")
    List<Note> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM notes WHERE title LIKE :titulo")
    Note findByTitle(String titulo);

    @Query("UPDATE notes SET title = :newTitle WHERE id_note = :id_nota")
    void updateTitle(int id_nota, String newTitle);

    @Query("UPDATE notes SET description = :newNote WHERE id_note = :id_nota")
    void updateNote(int id_nota, String newNote);

    @Insert
    void insertNote(Note note);

    @Insert
    void insertAll(List<Note> notes);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM notes WHERE id_note = :id_nota")
    void delete(int id_nota);

}