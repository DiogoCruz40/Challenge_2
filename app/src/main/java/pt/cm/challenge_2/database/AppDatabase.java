package pt.cm.challenge_2.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import pt.cm.challenge_2.database.daos.NotesDAO;
import pt.cm.challenge_2.database.entities.Note;

@Database(entities = {Note.class}, exportSchema = false,version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final String DB_NAME = "challenge2_db";
    private static AppDatabase sInstance;
    private static final Object LOCK = new Object();

    public static synchronized AppDatabase getInstance(Context context)
    {
        if (sInstance == null) {
            // only allows one thread at a time to run the follow operations
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                                AppDatabase.class, AppDatabase.DB_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }
    public abstract NotesDAO notesDAO();
}
