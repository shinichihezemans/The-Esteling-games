package com.example.theestelinggames.assignmentgame;

import android.os.Handler;

public interface OnBTReceive {
    Handler getHandler();
    void setThreads(ConnectThread connectThread, BluetoothIOThread bluetoothIOThread);
}
