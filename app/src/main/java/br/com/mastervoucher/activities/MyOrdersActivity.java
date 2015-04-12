package br.com.mastervoucher.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.mastervoucher.R;
import br.com.mastervoucher.adapters.menulist.MyOrderAdapter;
import br.com.mastervoucher.models.Product;
import br.com.mastervoucher.models.ShopCartItem;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MyOrdersActivity extends ActionBarActivity {

    @InjectView(R.id.listview)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        ButterKnife.inject(this);

        setupListViewContent();
    }

    private void setupListViewContent() {
        View headerView = LayoutInflater.from(this).inflate(
                R.layout.header_view_event, null);
        ImageView logo = (ImageView) headerView.findViewById(R.id.image_event_logo);
        // TODO: set image and text for event name
        TextView textEventName = (TextView) headerView.findViewById(R.id.text_event_name);
        textEventName.setText("Tomorrowland 2015");

        listView.addHeaderView(headerView, null, false);

        List<ShopCartItem> items = new ArrayList<ShopCartItem>();
        items.add(new ShopCartItem(new Product("Minhas Compras"), 5));
        items.add(new ShopCartItem(new Product(), 5));
        items.add(new ShopCartItem(new Product(), 11));
        items.add(new ShopCartItem(new Product(), 8));

        MyOrderAdapter menuAdapter = new MyOrderAdapter(this, items);
        listView.setAdapter(menuAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(), "CLICKED", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.button_generate_qrcode)
    public void generateQrCode() {
        // TODO: generate qrcode and open new activity
    }
}
