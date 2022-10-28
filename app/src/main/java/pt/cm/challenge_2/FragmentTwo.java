package pt.cm.challenge_2;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class FragmentTwo extends Fragment {

    private MainActivity activitycontext;
    public FragmentTwo() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        setHasOptionsMenu(true);
        activitycontext = (MainActivity) inflater.getContext();
        return view;


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_frag_two, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}