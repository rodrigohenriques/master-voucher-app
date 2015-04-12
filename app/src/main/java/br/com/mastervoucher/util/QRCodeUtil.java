package br.com.mastervoucher.util;


import net.glxn.qrgen.android.QRCode;

import java.io.File;
import java.util.List;

import br.com.mastervoucher.models.ShopCartItem;

/**
 * Created by Isabel Porto on 12/04/2015.
 */
public class QRCodeUtil {

    public File generateQRCodeBy(List<ShopCartItem> shopCartItems) {
        JSONUtil jsonUtil = new JSONUtil();
        String string = jsonUtil.to(shopCartItems);
        return QRCode.from(string).file();
    }
}
