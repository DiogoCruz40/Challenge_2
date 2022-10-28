package pt.cm.challenge_2.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
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

    @Query("UPDATE notes SET title = :newtitle WHERE id_note = :id_nota")
    void updateTitle(int id_nota, String newtitle);

    @Insert
    void insertAll(Note... note);

    @Delete
    void delete(Note note);
}