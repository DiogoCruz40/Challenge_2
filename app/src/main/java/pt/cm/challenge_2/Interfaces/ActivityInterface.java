package pt.cm.challenge_2.Interfaces;

import androidx.fragment.app.Fragment;

import pt.cm.challenge_2.MainActivity;

public interface ActivityInterface {
    MainActivity getmainactivity();
    void changeFrag(Fragment fragment);
}
