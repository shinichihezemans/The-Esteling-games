package com.example.theestelinggames.QRcode;

import android.graphics.Bitmap;

import com.google.zxing.common.BitMatrix;


/**
 * Helper class for encoding barcodes as a Bitmap.
 * <p>
 * Adapted from QRCodeEncoder, from the zxing project:
 * https://github.com/zxing/zxing
 * <p>
 * Licensed under the Apache License, Version 2.0.
 * <p>
 * Modified by group A1 class 2019-2020 of Avans Hogeschool Breda
 */
public class BarcodeEncoderLite {
    private static final int WHITE = 0x00FFFFFF;
    private static final int BLACK = 0xFF000000;

    public Bitmap createBitmap(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = matrix.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
