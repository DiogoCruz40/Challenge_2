package pt.cm.challenge_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import pt.cm.challenge_2.Interfaces.FragmentChange;
import pt.cm.challenge_2.Interfaces.NoteMapperInterface;
import pt.cm.challenge_2.database.AppDatabase;
import pt.cm.challenge_2.database.entities.Note;
import pt.cm.challenge_2.dtos.NoteDTO;
import pt.cm.challenge_2.mappers.NoteMapper;

public class MainActivity extends AppCompatActivity implements FragmentChange {

    private AppDatabase mDb;
    private FragmentManager fm;
    SharedViewModel model;

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

        ArrayList<NoteDTO> notes = new ArrayList<NoteDTO>();
        notes.add(new NoteDTO("note1", "hello1"));
        notes.add(new NoteDTO("note2", "hello2"));
        notes.add(new NoteDTO("note3", "hello3"));

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // how to get all notes
                NoteMapperInterface noteMapperInterface = new NoteMapper();

                Note teste = noteMapperInterface.toEntityNote(new NoteDTO("note4", "hello4"));
                System.out.println(teste.title);

                Note teste2 = new Note();
                teste2.title = "teste2";
                teste2.description = "teste2";
                teste2.id_note = 2;

                NoteDTO teste2DTO = noteMapperInterface.toNoteDTO(teste2);

                List<Note> notesEntity = noteMapperInterface.toEntityNotes(notes);
                mDb.notesDAO().insertAll(notesEntity);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Running actions
                        //model.setNotes(notesDTO);
                    }
                });
            }
        });

        model  = new ViewModelProvider(this).get(SharedViewModel.class);
        initialTasks();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialTasks();
    }

    private void initialTasks() {
        // This is how to instantiate a new thread
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // how to get all notes
                NoteMapperInterface noteMapperInterface = new NoteMapper();
                List<NoteDTO> notesDTO = noteMapperInterface.toNotesDTO(mDb.notesDAO().getAll());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Running actions
                        model.setNotes(notesDTO);
                    }
                });
            }
        });
    }

    private void addNoteDB(NoteDTO noteDTO) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // how to add a new note
                NoteMapperInterface noteMapperInterface = new NoteMapper();
                Note note = noteMapperInterface.toEntityNote(noteDTO);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDb.notesDAO().insertNote(note);
                    }
                });
            }
        });
    }

    private void deleteNoteDB(NoteDTO noteDTO) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // how to add a new note
                NoteMapperInterface noteMapperInterface = new NoteMapper();
                Note note = noteMapperInterface.toEntityNote(noteDTO);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDb.notesDAO().delete(note);
                    }
                });
            }
        });
    }

    private void updateTitleNoteDB(NoteDTO noteDTO) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // how to update title of a note
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDb.notesDAO().updateTitle(noteDTO.getId(), noteDTO.getTitle());
                    }
                });
            }
        });
    }

    private void updateNoteDB(NoteDTO noteDTO) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // how to update title of a note
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDb.notesDAO().updateNote(noteDTO.getId(), noteDTO.getDescription());
                    }
                });
            }
        });
    }

    public void teste(String request, Class target){
        //SharedViewModel.class
        System.out.println(Class.class);
        //teste("teste", SharedViewModel.class);
        return;
    }

    @Override
    public void changeFrag(Fragment fragment) {
        fm.beginTransaction().replace(R.id.targetcontainer, fragment).addToBackStack(null).commit();
    }

    public void saveAndChangeFrag(Fragment fragment){
        FragmentTwo fragment2 = (FragmentTwo) fm.findFragmentById(R.id.targetcontainer);

        assert fragment2 != null;
        fragment2.saveAndChangeFrag();

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

    public void addNote(){
        FragmentOne fragment1 = (FragmentOne) fm.findFragmentById(R.id.targetcontainer);

        assert fragment1 != null;
        fragment1.createNewNotePopUp();

        fm.beginTransaction().replace(R.id.targetcontainer, new FragmentOne()).addToBackStack(null).commit();
    }

    //Insert here the operations in which menu button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.closemenu:
                changeFrag(new FragmentOne());
                return true;
            case R.id.plusmenu:
                addNote();
                return true;
            case R.id.savemenu:
                saveAndChangeFrag(new FragmentOne());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}