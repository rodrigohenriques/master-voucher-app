package br.com.mastervoucher.util;


import android.graphics.Bitmap;

import net.glxn.qrgen.android.QRCode;

/**
 * Created by Isabel Porto on 12/04/2015.
 */
public class QRCodeUtil {

    public Bitmap generateQRCodeBy(String string) {
        return QRCode.from(string).bitmap();
    }
}
