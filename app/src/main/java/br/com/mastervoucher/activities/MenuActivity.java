package br.com.mastervoucher.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
    ParallaxListView listview;

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
        MenuAdapter menuAdapter = new MenuAdapter(this, R.layout.adapter_menu, shopCart.getShopCartItems());
        listview.setAdapter(menuAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
