package com.example.theestelinggames;

import android.os.Handler;

public interface OnBTReceive {
    Handler handler();
    void setThreads(ConnectThread connectThread, BluetoothIOThread bluetoothIOThread);
}
