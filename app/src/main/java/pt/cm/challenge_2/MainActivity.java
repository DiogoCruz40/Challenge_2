package pt.cm.challenge_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import java.util.List;

import pt.cm.challenge_2.database.AppDatabase;
import pt.cm.challenge_2.database.AppExecutors;
import pt.cm.challenge_2.database.entities.Notes;

public class MainActivity extends AppCompatActivity implements FragmentChange {

    //private RecyclerView mRecyclerView;
//    private PersonAdaptor mAdapter;
    private AppDatabase mDb;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get database
        mDb = AppDatabase.getInstance(getApplicationContext());

        fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.targetcontainer, new FragmentOne())
                .commit();
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.my_options_menu, menu);

        return true;

    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveTasks();
    }

    private void retrieveTasks() {
        // This is how to instantiate a new thread
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // how to get all notes
                final List<Notes> notes = mDb.notesDAO().getAll();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Running actions
//                      mAdapter.setTasks(persons);
                    }
                });
            }
        });
    }

    @Override
    public void changefrag(Fragment fragment) {
        fm.beginTransaction().replace(R.id.targetcontainer, fragment).commit();
    }
}