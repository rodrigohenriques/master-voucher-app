package br.com.mastervoucher.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import br.com.mastervoucher.R;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {

    @InjectView(R.id.image_qrcode_anim)
    ImageView imageQrCodeAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.inject(this);

        Animation pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);
        imageQrCodeAnim.startAnimation(pulse);
    }

    @OnClick(R.id.button_read_qrcode)
    public void goToQrCodeActivity() {
        Intent intent = new Intent(this, QRCodeReaderActivity.class);
        startActivityForResult(intent, 1);
    }

}
