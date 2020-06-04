package com.example.theestelinggames.mqttconnection;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class Message {

    private int id;
    private String character;
    private int score;

    public Message(int id, String character) {
        this.id = id;
        this.character = character;
        this.score = 0;
    }

    public Message(int id, String character, int score) {
        this(id, character);
        this.score = score;
    }

    public String toJson() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id", id);
            jsonObject.put("character", character);
            jsonObject.put("score", score);

            return jsonObject.toString(4);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public MqttMessage jsonMessage() {
        return new MqttMessage(toJson().getBytes(StandardCharsets.UTF_8));
    }
}

