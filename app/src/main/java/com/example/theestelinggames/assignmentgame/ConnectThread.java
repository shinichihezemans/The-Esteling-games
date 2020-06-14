package com.example.theestelinggames.assignmentgame;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

public class ConnectThread extends Thread {

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private final BluetoothSocket mmSocket;
    private final BluetoothAdapter mBluetoothAdapter;
    private final OnBTReceive onBTReceive;

    /**
     * Basic constructor of ConnectThread.
     *
     * @param device      The connected bluetoothDevice.
     * @param onBTReceive The interface which handles the threads.
     */
    ConnectThread(BluetoothDevice device, OnBTReceive onBTReceive) {
        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
        BluetoothSocket tmp = null;
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        this.onBTReceive = onBTReceive;

        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mmSocket = tmp;
    }

    /**
     * Runs the BluetoothIOThread and connects the bluetoothSocket.
     */
    public void run() {
        // Cancel discovery because it will slow down the connection
        mBluetoothAdapter.cancelDiscovery();

        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            mmSocket.connect();
        } catch (IOException connectException) {
            // Unable to connect; close the socket and get out
            try {
                mmSocket.close();
            } catch (IOException closeException) {
                closeException.printStackTrace();
            }
            return;
        }

        // Do work to manage the connection (in a separate thread)
        BluetoothIOThread bluetoothIOThread = new BluetoothIOThread(mmSocket, onBTReceive);
        this.onBTReceive.setThreads(this, bluetoothIOThread);
        bluetoothIOThread.start();
    }
}