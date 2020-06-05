package com.example.theestelinggames.util;

import android.content.Context;
import android.widget.Toast;

import com.example.theestelinggames.scoreboardList.ScoreboardListActivity;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MQTTConnection {

    private final String URL = "tcp://maxwell.bps-software.nl:1883";
    private final String TOPIC_PUBLISH = "A1/TheEsstelingGames/AndroidData";
    private final String TOPIC_SUBSCRIBE = "A1/TheEsstelingGames/Scoreboard";

    private final String USERNAME = "androidTI";
    private final char[] PASSWORD = "&FN+g$$Qhm7j".toCharArray();
    private final int QOS = 2;

    private MemoryPersistence memoryPersistence;
    private String clientID;
    private MqttAndroidClient client;
    private String will;
    private Context context;

    public static MQTTConnection newMQTTConnection(Context context, String clientID) {
        return new MQTTConnection(context, clientID);
    }

    private MQTTConnection(Context context, String clientID) {
        this.context = context;
        this.memoryPersistence = new MemoryPersistence();
        this.clientID = clientID;
        this.client = new MqttAndroidClient(context, URL, clientID, memoryPersistence);
        this.will = this.clientID + " has disconnected";
    }

    //sends messages to topic androidData
    public void connectOUT(final Message message) {
        try {
            final IMqttToken token = client.connect(ConnectOptions());

            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    try {
                        final IMqttToken subToken = client.subscribe(TOPIC_PUBLISH, QOS);
                        subToken.setActionCallback(new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {

                                if (message.getText().equals("")) {
                                    sendMessage(message, true);
                                } else {

                                    sendMessage(message, false);
                                }

                                try {
                                    client.disconnect();
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

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    exception.printStackTrace();
                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private ScoreboardListActivity scoreboardListActivity;


    public void setScoreboardListActivity(ScoreboardListActivity scoreboardListActivity) {
        this.scoreboardListActivity = scoreboardListActivity;
    }

    //receives messages from topic scoreboard
    public void connectIN() {

        try {
            IMqttToken token = client.connect(ConnectOptions());

            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    try {
                        client.subscribe(TOPIC_SUBSCRIBE, QOS);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }

                    client.setCallback(new MqttCallback() {
                        @Override
                        public void connectionLost(Throwable cause) {
                            Toast toast = Toast.makeText(context, "Warning: you got disconnected callback", Toast.LENGTH_SHORT);
                            toast.show();
                        }

                        @Override
                        public void messageArrived(String topic, MqttMessage message) {

                            if(new String(message.getPayload()).equals("Clear scoreboard")){
                                scoreboardListActivity.clear();
                                scoreboardListActivity.update();
                            }else {

                                String s = new String(message.getPayload());
                                String[] split = s.split(",");
                                String username = split[0];
                                int score = Integer.parseInt(split[1]);

                                scoreboardListActivity.addScore(username, score);
                                scoreboardListActivity.update();
                            }

                        }

                        @Override
                        public void deliveryComplete(IMqttDeliveryToken token) {

                        }
                    });
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


    public void closeConnection() {
        client.unregisterResources();
        client.close();
    }


    private void sendMessage(Message message, boolean isJson) {
        try {
            if (isJson) {
                client.publish(TOPIC_PUBLISH, message.jsonMessage());
            } else {
                client.publish(TOPIC_PUBLISH, message.textMessage());
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private MqttConnectOptions ConnectOptions() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();

        mqttConnectOptions.setCleanSession(true);
        mqttConnectOptions.setUserName(USERNAME);
        mqttConnectOptions.setPassword(PASSWORD);
        mqttConnectOptions.setWill(TOPIC_PUBLISH, will.getBytes(), QOS, false);

        return mqttConnectOptions;
    }


}
