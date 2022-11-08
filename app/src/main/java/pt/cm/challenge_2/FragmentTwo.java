package pt.cm.challenge_2;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import pt.cm.challenge_2.Interfaces.ActivityInterface;


public class FragmentTwo extends Fragment {

    private ActivityInterface activityInterface;
    private SharedViewModel mViewModel;
    private EditText editTextNote;
    private View view;
    private int id;


    public FragmentTwo() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activityInterface = (ActivityInterface) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_two, container, false);
        setHasOptionsMenu(true);


        this.mViewModel = new ViewModelProvider(activityInterface.getmainactivity()).get(SharedViewModel.class);

        if (getArguments() != null) {
            id = getArguments().getInt("id");
        }

        String note = mViewModel.getNoteContentById(id);

        editTextNote =(EditText) view.findViewById(R.id.editTextTextPersonName2);
        editTextNote.setText(note);

        return view;
    }

    public void saveAndChangeFrag() {
        editTextNote =(EditText) view.findViewById(R.id.editTextTextPersonName2);
        mViewModel.changeNote(this.id, String.valueOf(editTextNote.getText()));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_frag_two, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}