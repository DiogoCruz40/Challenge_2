package pt.cm.challenge_2;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Objects;

import pt.cm.challenge_2.database.AppExecutors;
import pt.cm.challenge_2.dtos.NoteDTO;

public class SharedViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<NoteDTO>> notes = new MutableLiveData<ArrayList<NoteDTO>>();

    public void setNotes (ArrayList<NoteDTO> notes){
        this.notes.setValue(notes);
    }

    public ArrayList<NoteDTO> getNotes(){
        return this.notes.getValue();
    }

    public String getNoteContentById (int id){
        for (NoteDTO n: Objects.requireNonNull(this.notes.getValue()))
        {
            if(n.getId() == id){
                return n.getNote();
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

        ArrayList<NoteDTO> notesAux = this.notes.getValue();

        //TODO: Definir na BD e buscar o id
        NoteDTO note = new NoteDTO(4, title, "");

        notesAux.add(note);

        /*
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // how to get all notes
                final List<NoteDTO> notes = mDb.notesDAO().getAll();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Running actions here


                    }
                });
            }
        });*/

        this.notes.setValue(notesAux);
    }

    public void changeNote (int id, String note){
        for (NoteDTO n: Objects.requireNonNull(this.notes.getValue()))
        {
            if(n.getId() == id){
                //TODO: atualizar na BD
                n.setNote(note);
                break;
            }
        }
    }

    public void changeTitle (int id, String title){
        for (NoteDTO n: Objects.requireNonNull(this.notes.getValue()))
        {
            if(n.getId() == id){
                //TODO: atualizar na BD
                n.setTitle(title);
                break;
            }
        }
    }

    public void deleteNote (int id){

        //TODO: atualizar na BD
        ArrayList<NoteDTO> notesAux = this.notes.getValue();
        NoteDTO note = getNoteById(id);
        assert notesAux != null;
        notesAux.remove(note);
        this.notes.setValue(notesAux);

    }


}