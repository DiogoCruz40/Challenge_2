package pt.cm.challenge_2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.List;

import pt.cm.challenge_2.Interfaces.ActivityInterface;
import pt.cm.challenge_2.Interfaces.FragmentOneInterface;
import pt.cm.challenge_2.dtos.NoteDTO;

public class FragmentOne extends Fragment implements FragmentOneInterface {

    private SharedViewModel mViewModel;
    private ListAdapter adapter;
    private ActivityInterface activityInterface;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Spinner spinnermqttpopup;
    private EditText newNoteName, subscribetopic;
    private Button deleteNote, saveNewName, cancel, subscribe, unsubscribe;
    private int id;

    public FragmentOne() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activityInterface = (ActivityInterface) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        setHasOptionsMenu(true);
        this.mViewModel = new ViewModelProvider(activityInterface.getmainactivity()).get(SharedViewModel.class);
        mViewModel.getNotes().observe(getViewLifecycleOwner(), notes -> {
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.notes);
           adapter = new ListAdapter(notes, this);
           recyclerView.setHasFixedSize(true);
           recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
           recyclerView.setAdapter(adapter);
       });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_frag_one, menu);
        MenuItem menuItem = menu.findItem(R.id.searchbar);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search"); 
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<NoteDTO> notes = adapter.getNotes();
                if (s.length()!=0) {
                    List<NoteDTO> filteredList = new ArrayList<NoteDTO>();
                    for (NoteDTO n : notes) {
                        if (n.getTitle().toLowerCase().contains(s.toLowerCase())) {
                            filteredList.add(n);
                        }
                    }
                    if (filteredList.isEmpty()) {
                        Toast.makeText(activityInterface.getmainactivity(), "No Results for your search", Toast.LENGTH_LONG).show();
                    } else {
                        adapter.setFilteredNotes(filteredList);
                    }
                } else {
                    adapter.setFilteredNotes(notes);
                }

                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onItemClick(int position, View v) {

//        System.out.println("click on item: " + adapter.getNotes().get(position).getTitle());

        FragmentTwo fr = new FragmentTwo();
        Bundle arg = new Bundle();
        if(adapter.getNotes().size() == adapter.getFilteredNotes().size())
        id = adapter.getNotes().get(position).getId();
        else
        {
            id = adapter.getFilteredNotes().get(position).getId();
        }
        arg.putInt("id", id);
        fr.setArguments(arg);

        activityInterface.changeFrag(fr);
    }

    @Override
    public void onLongItemClick(int position, View v) {
        if(adapter.getNotes().size() == adapter.getFilteredNotes().size())
            id = adapter.getNotes().get(position).getId();
        else
        {
            id = adapter.getFilteredNotes().get(position).getId();
        }
        createNewTitleDeletePopUp();
//        System.out.println("long click on item: " + adapter.getNotes().get(position).getTitle());
    }

    public void createNewTitleDeletePopUp(){
        // TODO: Bug ao criar uma segunda nota em sequencia, no deleteedit popup o edit text do titulo da nota esta semelhante a nota anterior

        dialogBuilder = new AlertDialog.Builder(activityInterface.getmainactivity());
        final View newTitleDeletePopUp = getLayoutInflater().inflate(R.layout.new_title_delete_popup, null);
        newNoteName = (EditText) newTitleDeletePopUp.findViewById(R.id.newNoteName);
        newNoteName.setText(mViewModel.getNoteById(id).getTitle());

        deleteNote = (Button) newTitleDeletePopUp.findViewById(R.id.deleteButton);
        saveNewName = (Button) newTitleDeletePopUp.findViewById(R.id.editNameButton);
        cancel = (Button) newTitleDeletePopUp.findViewById(R.id.cancelButton);

        dialogBuilder.setView(newTitleDeletePopUp);
        dialog = dialogBuilder.create();
        dialog.show();

        deleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mViewModel.deleteNote(id);
                activityInterface.getmainactivity();

                dialog.dismiss();
            }
        });

        saveNewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newNoteName =(EditText) newTitleDeletePopUp.findViewById(R.id.newNoteName);
                mViewModel.changeTitle(id, String.valueOf(newNoteName.getText()));

                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }


    public void createNewNotePopUp(){
        dialogBuilder = new AlertDialog.Builder(activityInterface.getmainactivity());
        final View newNotePopUp = getLayoutInflater().inflate(R.layout.new_note_popup, null);
        newNoteName = (EditText) newNotePopUp.findViewById(R.id.newTitle);

        saveNewName = (Button) newNotePopUp.findViewById(R.id.save);
        cancel = (Button) newNotePopUp.findViewById(R.id.cancel);

        dialogBuilder.setView(newNotePopUp);
        dialog = dialogBuilder.create();
        dialog.show();

        saveNewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newNoteName =(EditText) newNotePopUp.findViewById(R.id.newTitle);
                mViewModel.addNote(String.valueOf(newNoteName.getText()));

                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    public void mqttPopUp(){
        dialogBuilder = new AlertDialog.Builder(activityInterface.getmainactivity());
        final View mqttPopUp = getLayoutInflater().inflate(R.layout.mqtt_popup, null);
        cancel = (Button) mqttPopUp.findViewById(R.id.cancelbuttonmqtt);
        subscribe =  (Button) mqttPopUp.findViewById(R.id.subscribebuttonmqtt);
        unsubscribe = (Button) mqttPopUp.findViewById(R.id.unsubbuttonmqtt);
        spinnermqttpopup = (Spinner) mqttPopUp.findViewById(R.id.spinnermqtt);

        mViewModel.getTopics().observe(activityInterface.getmainactivity(), topics -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activityInterface.getmainactivity(), android.R.layout.simple_spinner_item, topics);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnermqttpopup.setAdapter(adapter);
        });
        dialogBuilder.setView(mqttPopUp);
        dialog = dialogBuilder.create();
        dialog.show();

        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscribetopic = (EditText) mqttPopUp.findViewById(R.id.subscribemqtt);
                if(mViewModel.subscribeToTopic(subscribetopic.getText().toString()))
                    subscribetopic.setText("");
                else
                    Toast.makeText(activityInterface.getmainactivity(),"Already subscribed",Toast.LENGTH_SHORT).show();
            }
        });

        unsubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinnermqttpopup.getSelectedItem() != null)
                    mViewModel.unsubscribeToTopic(spinnermqttpopup.getSelectedItem().toString());
                else
                    Toast.makeText(activityInterface.getmainactivity(),"Nothing to unsubscribe",Toast.LENGTH_SHORT).show();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    public void mqttMsgPopUp(String topic, MqttMessage message)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activityInterface.getmainactivity());
        final View mqttmesagePopUp = getLayoutInflater().inflate(R.layout.mqtt_message_popup, null);
        Button confirm = (Button) mqttmesagePopUp.findViewById(R.id.confirmmsgbtnmqtt);
        Button cancel =  (Button) mqttmesagePopUp.findViewById(R.id.cancelmsgbtnmqtt2);
        TextView topico = (TextView) mqttmesagePopUp.findViewById(R.id.topicmsgmqtt);
        TextView titulo = (TextView) mqttmesagePopUp.findViewById(R.id.titlemsgmqtt);
        NoteDTO noteDTO = new Gson().fromJson(message.toString(), NoteDTO.class);
        topico.setText(topic);
        titulo.setText(noteDTO.getTitle());

        dialogBuilder.setView(mqttmesagePopUp);
        Dialog dialog = dialogBuilder.create();
        dialog.show();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.insertMqttNote(noteDTO.getTitle(),noteDTO.getDescription());
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}