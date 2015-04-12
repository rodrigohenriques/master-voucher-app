package br.com.mastervoucher.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import br.com.mastervoucher.R;
import br.com.mastervoucher.dao.EventDAO;
import br.com.mastervoucher.util.AppType;
import br.com.mastervoucher.util.Extras;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {

    @InjectView(R.id.image_qrcode_anim)
    ImageButton imageQrCodeAnim;

    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.inject(this);

        getSupportActionBar().hide();

        Animation pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);
        imageQrCodeAnim.startAnimation(pulse);
    }

    @OnClick(R.id.image_qrcode_anim)
    public void goToQrCodeActivity() {
        Intent intent = new Intent(this, QRCodeReaderActivity.class);
        startActivityForResult(intent, 1);
       // getEvent("3");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            String jsonData = data.getStringExtra(Extras.QRCODE_RESULT);

            try {
                JSONObject jsonObject = new JSONObject(jsonData);

                String appType = jsonObject.getString(AppType.KEY);

                if (AppType.MERCHANT_TYPE.equals(appType)) {
                    String jsonDeliveryInfo = jsonObject.getString("deliveryInfo");

                    Intent intent = new Intent(HomeActivity.this, OrderActivity.class);
                    intent.putExtra(Extras.DELIVERY_INFO, jsonDeliveryInfo);
                    startActivity(intent);
                    finish();
                } else {
                    String eventId = jsonObject.getString("eventId");
                    getEvent(eventId);
                }
            } catch (Exception e) {
                Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void getEvent(String eventId) {
        EventDAO dao = new EventDAO(this);
        dao.saveEventId(eventId);
        Intent intent = new Intent(HomeActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}
