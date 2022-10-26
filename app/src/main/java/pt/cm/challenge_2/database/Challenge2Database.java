package pt.cm.challenge_2.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import pt.cm.challenge_2.database.daos.NotesDAO;
import pt.cm.challenge_2.database.entities.Notes;

@Database(entities = {Notes.class}, exportSchema = false,version = 1)
public abstract class Challenge2Database extends RoomDatabase {
    private static final String LOG_TAG = Challenge2Database.class.getSimpleName();
    private static final String DB_NAME = "challenge2_db";
    private static Challenge2Database sInstance;
    private static final Object LOCK = new Object();

    public static synchronized Challenge2Database getInstance(Context context)
    {
        if (sInstance == null) {
            // only allows one thread at a time to run the follow operations
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                                Challenge2Database.class, Challenge2Database.DB_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }
    public abstract NotesDAO notesDAO();
}
