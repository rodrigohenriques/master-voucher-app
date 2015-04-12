package br.com.mastervoucher.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import br.com.mastervoucher.R;
import br.com.mastervoucher.dao.EventDAO;
import br.com.mastervoucher.models.Event;
import br.com.mastervoucher.service.EventService;
import br.com.mastervoucher.util.Extras;
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

                String eventId = jsonObject.getString("eventId");
                String appType = jsonObject.getString("appType");
                getEvent(eventId);
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
