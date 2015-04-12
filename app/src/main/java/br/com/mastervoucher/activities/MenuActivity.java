package br.com.mastervoucher.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.nirhart.parallaxscroll.views.ParallaxListView;

import br.com.mastervoucher.R;
import br.com.mastervoucher.adapters.menulist.Header;
import br.com.mastervoucher.adapters.menulist.Item;
import br.com.mastervoucher.adapters.menulist.ListItem;
import br.com.mastervoucher.adapters.menulist.MenuListAdapter;
import br.com.mastervoucher.models.Product;
import br.com.mastervoucher.models.ShopCart;
import br.com.mastervoucher.models.ShopCartItem;
import br.com.mastervoucher.util.Extras;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

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
        //MenuAdapter menuAdapter = new MenuAdapter(this, R.layout.adapter_menu, shopCart.getShopCartItems());

        List<Item> items = new ArrayList<Item>();
        items.add(new Header("Header 1"));
        items.add(new ListItem(new ShopCartItem(new Product(), 5)));
        items.add(new ListItem(new ShopCartItem(new Product(), 11)));
        items.add(new Header("Header 2"));
        items.add(new ListItem(new ShopCartItem(new Product(), 2)));
        items.add(new ListItem(new ShopCartItem(new Product(), 3)));
        items.add(new ListItem(new ShopCartItem(new Product(), 12)));
        items.add(new ListItem(new ShopCartItem(new Product(), 5)));
        items.add(new ListItem(new ShopCartItem(new Product(), 8)));

        MenuListAdapter adapter = new MenuListAdapter(this, items);
        listview.setAdapter(adapter);
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
