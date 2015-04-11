package br.com.mastervoucher.reader;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class QRCodeReaderActivity extends ActionBarActivity implements QRCodeReaderView.OnQRCodeReadListener {

    public static final String RESULT_QRCODE_INFO = "qrcode_info";
    
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

        data.putExtra(RESULT_QRCODE_INFO, s);

        setResult(RESULT_OK, data);

        finish();
    }

    @Override
    public void cameraNotFound() {

    }

    @Override
    public void QRCodeNotFoundOnCamImage() {

    }
}
