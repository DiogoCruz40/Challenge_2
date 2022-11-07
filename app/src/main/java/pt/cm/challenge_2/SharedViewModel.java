package pt.cm.challenge_2;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import pt.cm.challenge_2.Interfaces.NoteMapperInterface;
import pt.cm.challenge_2.database.AppDatabase;
import pt.cm.challenge_2.database.entities.Note;
import pt.cm.challenge_2.dtos.NoteDTO;
import pt.cm.challenge_2.mappers.NoteMapper;

public class SharedViewModel extends AndroidViewModel {

    private final MutableLiveData<List<NoteDTO>> notes = new MutableLiveData<List<NoteDTO>>();
    private AppDatabase mDb;

    public SharedViewModel(@NonNull Application application) {
        super(application);
    }

    public void setNotes (List<NoteDTO> notes){
        this.notes.setValue(notes);
    }

    public MutableLiveData<List<NoteDTO>> getNotes(){
        return this.notes;
    }

    public String getNoteContentById (int id){
        for (NoteDTO n: Objects.requireNonNull(this.notes.getValue()))
        {
            if(n.getId() == id){
                return n.getDescription();
            }
        }
        return null;
    }

    public NoteDTO getNoteById (int id){
        for (NoteDTO n: Objects.requireNonNull(this.notes.getValue()))
        {
            if(n.getId() == id){
                return n;
            }
        }
        return null;
    }

    public void addNote (String title){

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // how to add notes
                NoteMapperInterface noteMapperInterface = new NoteMapper();
                mDb.notesDAO().insertNote(noteMapperInterface.toEntityNote(new NoteDTO(title, "")));

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        List<NoteDTO> notesAux = notes.getValue();
                        notesAux.add(new NoteDTO(title, ""));
                        notes.setValue(notesAux);
                    }
                });
            }
        });
    }

    public void changeNote (int id, String note){

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // how to change note's description
                mDb.notesDAO().updateNote(id, note);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        for (NoteDTO n: Objects.requireNonNull(notes.getValue()))
                        {
                            if(n.getId() == id){
                                n.setDescription(note);
                                break;
                            }
                        }
                    }
                });
            }
        });
    }

    public void changeTitle (int id, String title){

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // how to change note's title
                mDb.notesDAO().updateTitle(id, title);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        for (NoteDTO n: Objects.requireNonNull(notes.getValue()))
                        {
                            if(n.getId() == id){
                                //TODO: bug - o titulo não muda logo, nec. dar set da lista inteira, mas eu nao quero fazer isso, por isso ver outra solucao!
                                n.setTitle(title);
                                break;
                            }
                        }
                    }
                });
            }
        });
    }

    public void deleteNote (int id){

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // how to delete a note

                mDb.notesDAO().delete(id);

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        List<NoteDTO> notesAux = notes.getValue();
                        notesAux.remove(getNoteById(id));
                        notes.setValue(notesAux);
                    }
                });
            }
        });

    }

    public void startDB(){
        mDb = AppDatabase.getInstance(getApplication().getApplicationContext());

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // how to get all notes
                NoteMapperInterface noteMapperInterface = new NoteMapper();
                List<NoteDTO> notesDTO = noteMapperInterface.toNotesDTO(mDb.notesDAO().getAll());

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {

                        if(notesDTO == null){
                            notes.setValue(new ArrayList<NoteDTO>());
                        }
                        else{
                            notes.setValue(notesDTO);
                        }

                    }
                });
            }
        });
    }

    private void deleteAllNotes(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // delete all notes
                List<Note> notesBD = mDb.notesDAO().getAll();

                for (Note n : notesBD) {
                    mDb.notesDAO().delete(n);
                }

            }
        });
    }


}