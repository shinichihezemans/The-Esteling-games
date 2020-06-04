package com.example.theestelinggames;

import android.bluetooth.BluetoothSocket;
import android.os.Message;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BluetoothIOThread extends Thread {

    private volatile boolean exit = false;

    private OnBTReceive onBTReceiveCallBack;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private BluetoothSocket bluetoothSocket;

    public BluetoothIOThread(BluetoothSocket bluetoothSocket, OnBTReceive onBTReceive) {

        this.bluetoothSocket = bluetoothSocket;
        this.onBTReceiveCallBack = onBTReceive;

        try {
            this.dataOutputStream = new DataOutputStream(this.bluetoothSocket.getOutputStream());
            this.dataInputStream = new DataInputStream(this.bluetoothSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Message message = Message.obtain();
        message.obj = "CONNECTED";
        this.onBTReceiveCallBack.handler().sendMessage(message);
    }

    private StringBuilder msg = new StringBuilder();

    public void run() {
        while (!exit){

            if(!bluetoothSocket.isConnected()){
                this.cancel();
            }

            try{
                int bytesAvailable = this.dataInputStream.available();
                if(bytesAvailable > 0){
                    byte[] rawBytes = new byte[bytesAvailable];
                    dataInputStream.read(rawBytes);
                    for (int i = 0; i < bytesAvailable; i++) {
                        char received = (char) rawBytes[i];

                        if(received == '*'){
                            Message message = Message.obtain();
                            message.obj = msg.toString();
                            onBTReceiveCallBack.handler().sendMessage(message);
                            msg = new StringBuilder();
                            break;
                        }

                        msg.append(received);
                    }
                }
            } catch (IOException e){
                e.printStackTrace();
                this.cancel();
            }
        }
    }

    public void writeUTF(String msg){
        try {
            this.dataOutputStream.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancel() {
        try {
            exit = true;
            bluetoothSocket.close();
        } catch (IOException e) { }

        Message message = Message.obtain();
        message.obj = "DISCONNECTED";
        this.onBTReceiveCallBack.handler().sendMessage(message);
    }
}
