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

    /**
     * Basic constructor of Message.
     *
     * @param id        The user ID.
     * @param character The character which the user has chosen.
     * @param score     The score which the user has been assigned.
     */
    public Message(int id, String character, int score) {
        this.id = id;
        this.character = character;
        this.score = score;
        this.text = "";
    }

    /**
     * Helper constructor of Message.
     *
     * @param id        The user ID.
     * @param character The character which the user has chosen.
     */
    public Message(int id, String character) {
        this(id, character, 0);
    }

    /**
     * Helper constructor of Message.
     *
     * @param text The message which the user sends to the server.
     */
    public Message(String text) {
        this(0, "", 0);
        this.text = text;
    }

    /**
     * Getter for the text message.
     *
     * @return The variable text
     */
    String getText() {
        return text;
    }

    /**
     * Creates a MQTT message.
     *
     * @return MQTT message with the text message.
     */
    MqttMessage textMessage() {
        return new MqttMessage(getText().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Creates a JSON string.
     *
     * @return A string in the format of a JSON file.
     */
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

    /**
     * Creates a MQTT message.
     *
     * @return MQTT message with the JSON string.
     */
    MqttMessage jsonMessage() {
        return new MqttMessage(toJson().getBytes(StandardCharsets.UTF_8));
    }
}

