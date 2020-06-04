package com.example.theestelinggames.assignmentgame;

import android.os.Handler;

public interface OnBTReceive {
    Handler handler();
    void setThreads(ConnectThread connectThread, BluetoothIOThread bluetoothIOThread);
}
