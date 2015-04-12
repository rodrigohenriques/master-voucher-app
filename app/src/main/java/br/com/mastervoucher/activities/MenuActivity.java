package br.com.mastervoucher.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nirhart.parallaxscroll.views.ParallaxListView;

import br.com.mastervoucher.R;
import br.com.mastervoucher.adapters.MenuAdapter;
import br.com.mastervoucher.models.ShopCart;
import br.com.mastervoucher.util.Extras;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by AlexandreMarones on 4/11/15.
 */
public class MenuActivity extends BaseActivity {

    @InjectView(R.id.listview)
    ParallaxListView listView;

    public ShopCart shopCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ButterKnife.inject(this);

        shopCart = (ShopCart) getIntent().getSerializableExtra(Extras.SHOP_CART);

        setupListViewContent();
    }

    private void setupListViewContent() {
        View headerView = LayoutInflater.from(this).inflate(
                R.layout.header_view_event, null);
        ImageView logo = (ImageView) headerView.findViewById(R.id.image_event_logo);
        // TODO: set image and text for event name
        TextView textEventName = (TextView) headerView.findViewById(R.id.text_event_name);
        textEventName.setText("Tomorrowland 2015");

        listView.addParallaxedHeaderView(headerView, null, false);

        MenuAdapter menuAdapter = new MenuAdapter(this, R.layout.adapter_menu, shopCart.getShopCartItems());
        listView.setAdapter(menuAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(), "CLICKED", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.button_buy)
    public void paymentWithCard() {
        Intent intent = new Intent(this, PaymentActivity.class);
        startActivity(intent);
    }
}
