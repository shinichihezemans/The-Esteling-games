package com.example.theestelinggames.mqttconnection;

import android.content.Context;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;

public class MQTTConnection {

    private final String URL = "tcp://maxwell.bps-software.nl:1883";
    private final String TOPIC = "A1/TheEsstelingGames/AndroidData";
    private final String USERNAME = "androidTI";
    private final char[] PASSWORD = "&FN+g$$Qhm7j".toCharArray();
    private final int QOS = 2;

    private MemoryPersistence memoryPersistence;
    private String clientID;
    private MqttAndroidClient client;
    private String will;

    public static MQTTConnection newMQTTConnection(Context context, String clientID) {
        return new MQTTConnection(context, clientID);
    }

    private MQTTConnection(Context context, String clientID) {
        this.memoryPersistence = new MemoryPersistence();
        this.clientID = clientID;
        this.client = new MqttAndroidClient(context, URL, clientID, memoryPersistence);
        this.will = this.clientID + " has disconnected";
    }

    public void connectWithMessage(final int id, final String animalName) {
        try {
            IMqttToken token = client.connect(ConnectOptions());

            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    try {
                        IMqttToken subToken = client.subscribe(TOPIC, QOS);
                        subToken.setActionCallback(new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                sendMessage(new Message(id, animalName));
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                exception.printStackTrace();
                            }
                        });
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    exception.printStackTrace();
                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    private void sendMessage(Message message) {
        try {
            client.publish(TOPIC, message.jsonMessage());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private MqttConnectOptions ConnectOptions() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();

        mqttConnectOptions.setCleanSession(true);
        mqttConnectOptions.setUserName(USERNAME);
        mqttConnectOptions.setPassword(PASSWORD);
        mqttConnectOptions.setWill(TOPIC, will.getBytes(), QOS, false);

        return mqttConnectOptions;
    }


}
