package br.com.mastervoucher.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import br.com.mastervoucher.R;
import br.com.mastervoucher.models.Event;
import br.com.mastervoucher.service.EventService;
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

        gson = new GsonBuilder().disableHtmlEscaping().create();

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
                getEvent(eventId);
            } catch (Exception e) {

            }
        }
    }

    private void getEvent(String eventId) {


        EventService eventService = new EventService();

        eventService.getEvent(eventId, new JsonHttpResponseHandler() {
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
