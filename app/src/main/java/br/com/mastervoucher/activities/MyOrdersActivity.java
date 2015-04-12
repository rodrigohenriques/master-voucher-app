package br.com.mastervoucher.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.mastervoucher.R;
import br.com.mastervoucher.adapters.menulist.MyOrderAdapter;
import br.com.mastervoucher.dao.EventDAO;
import br.com.mastervoucher.models.DeliveryItem;
import br.com.mastervoucher.models.ShopCart;
import br.com.mastervoucher.models.ShopCartItem;
import br.com.mastervoucher.service.CustomerService;
import br.com.mastervoucher.util.AppType;
import br.com.mastervoucher.util.Extras;
import br.com.mastervoucher.util.JSONUtil;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MyOrdersActivity extends ActionBarActivity {

    @InjectView(R.id.listview)
    ListView listView;

    EventDAO dao;

    List<ShopCartItem> shopCartItems;
    List<DeliveryItem> items;

    boolean hasHeader = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        ButterKnife.inject(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        dao = new EventDAO(this);
        shopCartItems = new ArrayList<>();

        if (dao.hasEventId()) {
            getCustomerItems();
        } else {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void getCustomerItems() {
        CustomerService customerService = new CustomerService();
        customerService.getItems(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    int count = response.length();

                    if (count > 0) {

                        for (int i = 0; i < count; i++) {

                            String json = response.getString(i);
                            ShopCartItem item = new JSONUtil().from(json, ShopCartItem.class);

                            shopCartItems.add(item);
                        }

                        setupListViewContent();

                    } else {
                        Intent intent = new Intent(MyOrdersActivity.this, MenuActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {
                    Toast.makeText(MyOrdersActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    private void setupListViewContent() {
        if(!hasHeader) {
            View headerView = LayoutInflater.from(this).inflate(
                    R.layout.header_view_order, null);
            ImageView logo = (ImageView) headerView.findViewById(R.id.image_event_logo);
            // TODO: set image and text for event name
            TextView textEventName = (TextView) headerView.findViewById(R.id.text_event_name);
            textEventName.setText(dao.getEventName());
            ImageButton buttonNewBuy = (ImageButton) headerView.findViewById(R.id.button_new_buy);

            buttonNewBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyOrdersActivity.this, MenuActivity.class);
                    startActivity(intent);
                }
            });

            listView.addHeaderView(headerView, null, false);
            hasHeader = true;
        }

        final MyOrderAdapter menuAdapter = new MyOrderAdapter(this, getDeliveredItems());
        listView.setAdapter(menuAdapter);
        listView.setDividerHeight(0);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                menuAdapter.click(position, view);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                menuAdapter.longClick(position, view);
                return true;
            }
        });
    }

    private List<DeliveryItem> getDeliveredItems() {
        items = new ArrayList<>();
        for (ShopCartItem shopCartItem : shopCartItems) {
            DeliveryItem deliveryItem = new DeliveryItem(shopCartItem);
            items.add(deliveryItem);
        }
        return items;

    }

    @OnClick(R.id.button_generate_qrcode)
    public void generateQrCode() {
        // TODO: generate qrcode and open new activity
        try {
            String deliveryJson = writeDeliveryJson();

            if (deliveryJson != null) {
                Intent intent = new Intent(MyOrdersActivity.this, ReceiverActivity.class);

                intent.putExtra(Extras.QRCODE_DATA, deliveryJson);

                startActivity(intent);
            } else {
                Toast.makeText(this, "Erro!", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public String writeDeliveryJson() throws JSONException {

        JSONObject jsonResult = new JSONObject();

        jsonResult.put(AppType.KEY, AppType.MERCHANT_TYPE);

        JSONArray jsonArray = new JSONArray();

        for (DeliveryItem item : items) {

            JSONObject jsonObject = new JSONObject();

            if (item.quantityDelivered > 0) {
                jsonObject.put("product_id", item.product.id);
                jsonObject.put("quantity", item.quantityDelivered);

                jsonArray.put(jsonObject);
            }
        }

        jsonResult.put("deliveryInfo", jsonArray);

        return jsonResult.toString();
    }


}
