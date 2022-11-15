package pt.cm.challenge_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import pt.cm.challenge_2.Interfaces.ActivityInterface;
import pt.cm.challenge_2.dtos.NoteDTO;

public class MainActivity extends AppCompatActivity implements ActivityInterface {

    private FragmentManager fm;
    private SharedViewModel model;

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
        model.getToastObserver().observe(this, message -> {
            Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        });
        model.connmqtt(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        model.startDB();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.disconmqtt();
    }

    @Override
    public void changeFrag(Fragment fragment) {
        fm.beginTransaction().replace(R.id.targetcontainer, fragment).addToBackStack(null).commit();
    }

    private void saveAndChangeFrag(Fragment fragment){
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

    private void addNote(){
        FragmentOne fragment1 = (FragmentOne) fm.findFragmentById(R.id.targetcontainer);

        assert fragment1 != null;
        fragment1.createNewNotePopUp();
    }

    private void mqttopenpopup(){
        FragmentOne fragment1 = (FragmentOne) fm.findFragmentById(R.id.targetcontainer);

        assert fragment1 != null;
        fragment1.mqttPopUp();
    }

    @Override
    public void msgmqttpopup(String topic, MqttMessage message){
        FragmentOne fragment1 = (FragmentOne) fm.findFragmentById(R.id.targetcontainer);

        assert fragment1 != null;
        fragment1.mqttMsgPopUp(topic,message);
    }

    //Insert here the operations in which menu button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.mqttmenu:
                mqttopenpopup();
                return true;
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