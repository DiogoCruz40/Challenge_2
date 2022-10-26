package pt.cm.challenge_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements FragmentChange{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.targetcontainer, new FragmentOne())
                .commit();
    }

    @Override
    public void changefrag(Fragment fragment) {
        this.getSupportFragmentManager().beginTransaction().replace(R.id.targetcontainer, fragment).commit();
    }
}