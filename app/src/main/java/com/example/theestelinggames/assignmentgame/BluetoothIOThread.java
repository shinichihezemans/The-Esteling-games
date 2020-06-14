package com.example.theestelinggames.assignmentgame;

import android.bluetooth.BluetoothSocket;
import android.os.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * class which makes a new Thread for the bluetooth
 */
public class BluetoothIOThread extends Thread {

    private volatile boolean exit = false;

    private OnBTReceive onBTReceiveCallBack;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private BluetoothSocket bluetoothSocket;
    private StringBuilder msg;

    /**
     * Basic constructor of BluetoothIOThread.
     *
     * @param bluetoothSocket The bluetoothSocket connection.
     * @param onBTReceive     The interface that handles the threads.
     */
    BluetoothIOThread(BluetoothSocket bluetoothSocket, OnBTReceive onBTReceive) {
        this.bluetoothSocket = bluetoothSocket;
        this.onBTReceiveCallBack = onBTReceive;
        this.msg = new StringBuilder();

        try {
            this.dataOutputStream = new DataOutputStream(this.bluetoothSocket.getOutputStream());
            this.dataInputStream = new DataInputStream(this.bluetoothSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Message message = Message.obtain();
        message.obj = "CONNECTED";
        this.onBTReceiveCallBack.getHandler().sendMessage(message);
    }

    /**
     * Runs the thread and reads the incoming bytes.
     */
    public void run() {
        while (!exit) {

            if (!bluetoothSocket.isConnected()) {
                this.cancel();
            }

            try {
                int bytesAvailable = this.dataInputStream.available();
                if (bytesAvailable > 0) {
                    byte[] rawBytes = new byte[bytesAvailable];
                    dataInputStream.read(rawBytes);
                    for (int i = 0; i < bytesAvailable; i++) {
                        char received = (char) rawBytes[i];

                        if (received == '*') {
                            Message message = Message.obtain();
                            message.obj = msg.toString();
                            onBTReceiveCallBack.getHandler().sendMessage(message);
                            msg = new StringBuilder();
                            break;
                        }

                        msg.append(received);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                this.cancel();
            }
        }
    }

    /**
     * Method to write the message "start" to the ESP.
     */
    void writeUTF() {
        try {
            this.dataOutputStream.writeUTF("start");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the bluetoothSocket.
     */
    void cancel() {
        try {
            exit = true;
            bluetoothSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Message message = Message.obtain();
        message.obj = "DISCONNECTED";
        this.onBTReceiveCallBack.getHandler().sendMessage(message);
    }
}
