package pt.cm.challenge_2;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import pt.cm.challenge_2.Interfaces.ClickListener;
import pt.cm.challenge_2.Interfaces.LongClickListener;
import pt.cm.challenge_2.dtos.NoteDTO;

public class FragmentOne extends Fragment implements ClickListener, LongClickListener {

    private SharedViewModel mViewModel;
    private ListAdapter adapter;
    private MainActivity activitycontext;

    public FragmentOne() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        setHasOptionsMenu(true);
        activitycontext = (MainActivity) inflater.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.notes);

        ArrayList<NoteDTO> notes = new ArrayList<NoteDTO>();
        notes.add(new NoteDTO("note1", "hello"));
        notes.add(new NoteDTO("note2", "hello"));
        notes.add(new NoteDTO("note3", "hello"));

        adapter = new ListAdapter(notes, this::onItemClick, this::onLongItemClick);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        recyclerView.setAdapter(adapter);

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
                ArrayList<NoteDTO> notes = adapter.getNotes();
                if (s.length()!=0) {
                    ArrayList<NoteDTO> filteredList = new ArrayList<NoteDTO>();
                    for (NoteDTO n : notes) {
                        if (n.getTitle().toLowerCase().contains(s.toLowerCase())) {
                            filteredList.add(n);
                        }
                    }
                    if (filteredList.isEmpty()) {
                        Toast.makeText(activitycontext, "No Results for your search", Toast.LENGTH_LONG).show();
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
        // TODO: change to fragment -> Miss passing info to frag 2
        System.out.println("click on item: " + adapter.getNotes().get(position).getTitle());
        activitycontext.changefrag(new FragmentTwo());
    }

    @Override
    public void onLongItemClick(int position, View v) {
        // TODO: pop up bs
        System.out.println("long click on item: " + adapter.getNotes().get(position).getTitle());
    }
}