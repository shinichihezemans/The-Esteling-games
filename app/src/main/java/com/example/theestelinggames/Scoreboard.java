package com.example.theestelinggames;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;

import java.util.ArrayList;

public class Scoreboard extends AppCompatActivity {

    MqttAndroidClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        String clientId = MqttClient.generateClientId();

        client = new MqttAndroidClient(this.getApplicationContext(), "tcp://maxwell.bps-software.nl:1883", clientId);

        try{

            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName("androidTI");
            options.setPassword("&FN+g$$Qhm7j".toCharArray());
            IMqttToken token = client.connect(options);

            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.i("Succes", "Connected");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.i("Failure", "Failed to connect");
                }
            });
        }catch (MqttException e){
            e.printStackTrace();
        }
    }

    public void onRefreshButtonClicked(View view) {
        try {
            IMqttToken token = this.client.subscribe("A1/Scoreboard", 2);
            MqttWireMessage message = token.getResponse();
        }catch (MqttException e){
            e.printStackTrace();
        }
    }

    public void sendData (String message) {
        try{
            IMqttToken token = this.client.publish("A1/Scoreboard", new MqttMessage(message.getBytes()));
        }catch (MqttException e){
            e.printStackTrace();
        }
    }
}