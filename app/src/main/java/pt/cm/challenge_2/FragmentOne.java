package pt.cm.challenge_2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FragmentOne extends Fragment implements ListAdapter.ClickListener ,ListAdapter.LongClickListener{

    private SharedViewModel mViewModel;
    private ListAdapter adapter;

    public FragmentOne(){}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.notes);

        ArrayList<Note> notes = new ArrayList<Note>();
        notes.add(new Note("note1","hello"));
        notes.add(new Note("note2","hello"));
        notes.add(new Note("note3","hello"));

        adapter = new ListAdapter(notes,this::onItemClick, this::onLongItemClick);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onItemClick(int position, View v) {
        // TODO: change to fragment
        System.out.println("click on item: " + adapter.getNotes().get(position).getTitle());
    }

    @Override
    public void onLongItemClick(int position, View v){
        // TODO: pop up bs
        System.out.println("long click on item: " + adapter.getNotes().get(position).getTitle());
    }
}