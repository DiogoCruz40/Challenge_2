package pt.cm.challenge_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;

import pt.cm.challenge_2.Interfaces.ActivityInterface;

public class MainActivity extends AppCompatActivity implements ActivityInterface {

    private FragmentManager fm;
    SharedViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.targetcontainer, new FragmentOne())
                .commit();

        model  = new ViewModelProvider(this).get(SharedViewModel.class);
        model.startDB();

    }

    @Override
    protected void onResume() {
        super.onResume();
        model.startDB();
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

    @Override
    public MainActivity getmainactivity() {
        return this;
    }
}