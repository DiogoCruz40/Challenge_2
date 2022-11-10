package pt.cm.challenge_2.Interfaces;

import androidx.fragment.app.Fragment;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import pt.cm.challenge_2.MainActivity;

public interface ActivityInterface {
    MainActivity getmainactivity();
    void changeFrag(Fragment fragment);
    void msgmqttpopup(String topic, MqttMessage message);
}
