package br.com.mastervoucher.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import br.com.mastervoucher.R;
import br.com.mastervoucher.adapters.OrderAdapter;
import br.com.mastervoucher.models.DeliveredItem;
import br.com.mastervoucher.service.DeliveryInfoService;
import br.com.mastervoucher.util.Extras;
import br.com.mastervoucher.util.JSONUtil;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class OrderActivity extends BaseActivity {

    @InjectView(R.id.listview)
    ListView listView;

    List<DeliveredItem> deliveredItems = new ArrayList<DeliveredItem>();;

    String deliveryInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ButterKnife.inject(this);
        getSupportActionBar().setTitle("Pedido");

        deliveryInfo = (String) getIntent().getExtras().get(Extras.DELIVERY_INFO);

        try {
            checkDeliveryInfo(deliveryInfo);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

//        deliveredItems = (List<DeliveredItem>) getIntent().getExtras().getSerializable(Extras.ORDER_LIST);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void setupListViewContent() {
        OrderAdapter menuAdapter = new OrderAdapter(this, deliveredItems);
        listView.setAdapter(menuAdapter);
    }

    @OnClick(R.id.button_confirm)
    public void confirmReceiver() {
        // TODO: request
        DeliveryInfoService deliveryInfoService = new DeliveryInfoService();

        try {
            deliveryInfoService.delivery(this, deliveryInfo, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);

                    try {
                        String status = response.getString("status");

                        if ( "ok".equals(status) ) {
                            Toast.makeText(OrderActivity.this, "Pedido entregue com suceso!", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(OrderActivity.this, "O pedido não pode ser entregue.", Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(OrderActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(OrderActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void checkDeliveryInfo(String deliveryInfo) throws UnsupportedEncodingException {
        DeliveryInfoService deliveryInfoService = new DeliveryInfoService();

        deliveryInfoService.checkDeliveryInfo(this, deliveryInfo, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    int count = response.length();

                    if (count > 0) {

                        ArrayList<DeliveredItem> items = new ArrayList<>();

                        for (int i = 0; i < count; i++) {

                            String json = response.getString(i);
                            DeliveredItem item = new JSONUtil().from(json, DeliveredItem.class);

                            items.add(item);
                        }

                        deliveredItems = items;

                        setupListViewContent();

                    } else {
                        Toast.makeText(OrderActivity.this, "ERRO!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(OrderActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

}
