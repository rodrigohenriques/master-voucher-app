package br.com.mastervoucher.activities;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import br.com.mastervoucher.R;
import br.com.mastervoucher.adapters.OrderAdapter;
import br.com.mastervoucher.models.DeliveredItem;
import br.com.mastervoucher.service.DeliveryInfoService;
import br.com.mastervoucher.util.Extras;
import br.com.mastervoucher.util.JSONUtil;
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

        deliveryInfo = (String) getIntent().getExtras().get(Extras.DELIVERY_INFO);

        try {
            checkDeliveryInfo(deliveryInfo);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        deliveredItems = (List<DeliveredItem>) getIntent().getExtras().getSerializable(Extras.ORDER_LIST);
    }

    private void setupListViewContent() {
        OrderAdapter menuAdapter = new OrderAdapter(this, deliveredItems);
        listView.setAdapter(menuAdapter);
    }

    @OnClick(R.id.button_confirm)
    public void confirmReceiver() throws UnsupportedEncodingException {
        // TODO: request
        DeliveryInfoService deliveryInfoService = new DeliveryInfoService();

        deliveryInfoService.delivery(this, deliveryInfo, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                try {
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
