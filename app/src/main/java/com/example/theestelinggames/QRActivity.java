package com.example.theestelinggames;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import com.example.theestelinggames.iconscreen.CharacterActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QRActivity extends AppCompatActivity {

    public static final String USERCREDENTIALS = "UserCredentials";
    private static final String LOGTAG = "QRActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        ImageView imageView = (ImageView) findViewById(R.id.qr_imageView);

        SharedPreferences sharedPreferences = getSharedPreferences(CharacterActivity.USERCREDENTIALS, MODE_PRIVATE);
        String clientID = sharedPreferences.getString(CharacterActivity.usernameKey, null);

        if (clientID != null) {
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(clientID);
            if (m.find()) {
                clientID = m.group();
            }

            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            try {
                int size = displayMetrics.widthPixels - (displayMetrics.widthPixels / 5);
                BitMatrix bitMatrix = multiFormatWriter.encode(clientID, BarcodeFormat.QR_CODE, size, size);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                imageView.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }
}