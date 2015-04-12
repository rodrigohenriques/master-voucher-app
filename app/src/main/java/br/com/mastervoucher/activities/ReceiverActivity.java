package br.com.mastervoucher.activities;

import android.os.Bundle;
import android.widget.ImageView;

import net.glxn.qrgen.android.QRCode;

import br.com.mastervoucher.R;
import br.com.mastervoucher.util.Extras;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class ReceiverActivity extends BaseActivity {

    @InjectView(R.id.view_qrcode)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

        setTitle("Código de Retirada");

        ButterKnife.inject(this);

        String qrCodeData = (String) getIntent().getExtras().get(Extras.QRCODE_DATA);

        imageView.setImageBitmap(QRCode.from(qrCodeData).bitmap());
    }
}
