package com.example.theestelinggames.qrcode;

import android.graphics.Bitmap;
import android.util.Log;

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
class BarcodeEncoderLite {
    private static final String LOGTAG = BarcodeEncoderLite.class.getName();

    private static final int WHITE = 0x00FFFFFF;
    private static final int BLACK = 0xFF000000;

    /**
     * Method to make the QR code bitmap.
     *
     * @param matrix The required bitMatrix.
     * @return The QR code bitMap.
     */
    Bitmap createBitmap(BitMatrix matrix) {
        Log.d(LOGTAG, "createBitmap()");
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
