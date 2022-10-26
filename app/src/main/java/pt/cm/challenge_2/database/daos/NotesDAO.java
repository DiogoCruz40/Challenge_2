package pt.cm.challenge_2.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import pt.cm.challenge_2.database.entities.Notes;

@Dao
public interface NotesDAO {
    @Query("SELECT * FROM notes")
    List<Notes> getAll();

    @Query("SELECT * FROM notes WHERE id_note IN (:userIds)")
    List<Notes> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM notes WHERE title LIKE :titulo")
    Notes findByTitle(String titulo);

    @Insert
    void insertAll(Notes... notes);

    @Delete
    void delete(Notes notes);
}