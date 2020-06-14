package com.example.theestelinggames.util;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class Message {

    private int id;
    private String character;
    private int score;
    private String text;


    public Message(int id, String character, int score) {
        this.id = id;
        this.character = character;
        this.score = score;
        this.text = "";
    }

    public Message(int id, String character) {
        this(id, character, 0);
    }

    public Message(String text) {
        this(0,"",0);
        this.text = text;
    }

    String getText() {
        return text;
    }

    MqttMessage textMessage() {
        return new MqttMessage(getText().getBytes(StandardCharsets.UTF_8));
    }

    private String toJson() {
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

    MqttMessage jsonMessage() {
        return new MqttMessage(toJson().getBytes(StandardCharsets.UTF_8));
    }
}

