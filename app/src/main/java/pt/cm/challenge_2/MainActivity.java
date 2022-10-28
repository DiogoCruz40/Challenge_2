package pt.cm.challenge_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;


import java.util.List;

import pt.cm.challenge_2.Interfaces.FragmentChange;
import pt.cm.challenge_2.database.AppDatabase;
import pt.cm.challenge_2.database.AppExecutors;
import pt.cm.challenge_2.database.entities.Note;

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

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void retrieveTasks() {
        // This is how to instantiate a new thread
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // how to get all notes
                final List<Note> notes = mDb.notesDAO().getAll();
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
        fm.beginTransaction().replace(R.id.targetcontainer, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    //Insert here the operations in which menu button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.closemenu:
                changefrag(new FragmentOne());
                return true;
            case R.id.plusmenu:
                return true;
            case R.id.savemenu:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}