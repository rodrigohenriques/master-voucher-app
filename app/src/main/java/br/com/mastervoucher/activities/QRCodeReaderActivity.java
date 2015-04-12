package br.com.mastervoucher.activities;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import br.com.mastervoucher.R;
import br.com.mastervoucher.util.Extras;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class QRCodeReaderActivity extends BaseActivity implements QRCodeReaderView.OnQRCodeReadListener {

    @InjectView(R.id.qrdecoderview)
    QRCodeReaderView qrCodeReaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_reader);

        ButterKnife.inject(this);

        qrCodeReaderView.setOnQRCodeReadListener(this);


    }

    @Override
    public void onQRCodeRead(String s, PointF[] pointFs) {
        Intent data = new Intent();

        data.putExtra(Extras.QRCODE_RESULT, s);

        setResult(RESULT_OK, data);

        finish();
    }

    @Override
    public void cameraNotFound() {

    }

    @Override
    public void QRCodeNotFoundOnCamImage() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        qrCodeReaderView.getCameraManager().startPreview();
    }

    @Override
    protected void onStop() {
        super.onStop();
        qrCodeReaderView.getCameraManager().stopPreview();
    }
}
