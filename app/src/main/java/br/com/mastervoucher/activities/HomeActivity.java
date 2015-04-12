package br.com.mastervoucher.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import br.com.mastervoucher.R;
import br.com.mastervoucher.dao.EventDAO;
import br.com.mastervoucher.models.Event;
import br.com.mastervoucher.service.DeliveryInfoService;
import br.com.mastervoucher.util.Extras;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {

    @InjectView(R.id.image_qrcode_anim)
    ImageView imageQrCodeAnim;

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

    @OnClick(R.id.button_read_qrcode)
    public void goToQrCodeActivity() {
//        Intent intent = new Intent(this, QRCodeReaderActivity.class);
//        startActivityForResult(intent, 1);
        getEvent("3");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            String jsonData = data.getStringExtra(Extras.QRCODE_RESULT);

            try {
                JSONObject jsonObject = new JSONObject(jsonData);

                String appType = jsonObject.getString("appType");

                if ("merchant".equals(appType)) {
                    String jsonDeliveryInfo = jsonObject.getString("deliveryInfo");

                    checkDeliveryInfo(jsonDeliveryInfo);
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

    private void checkDeliveryInfo(String deliveryInfo) throws UnsupportedEncodingException {
        DeliveryInfoService deliveryInfoService = new DeliveryInfoService();

        deliveryInfoService.checkDeliveryInfo(this, deliveryInfo, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Event event = gson.fromJson(response.toString(), Event.class);

                Intent intent = new Intent(HomeActivity.this, MenuActivity.class);

                intent.putExtra(Extras.EVENT, event);

                startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

    }
}
