package br.com.mastervoucher.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.nirhart.parallaxscroll.views.ParallaxListView;

import java.util.ArrayList;
import java.util.List;

import br.com.mastervoucher.R;
import br.com.mastervoucher.adapters.menulist.Header;
import br.com.mastervoucher.adapters.menulist.Item;
import br.com.mastervoucher.adapters.menulist.MenuListAdapter;
import br.com.mastervoucher.models.Event;
import br.com.mastervoucher.models.Product;
import br.com.mastervoucher.models.ShopCart;
import br.com.mastervoucher.util.Extras;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MenuActivity extends BaseActivity {

    @InjectView(R.id.listview)
    ParallaxListView listview;

    Event event;
    public ShopCart shopCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ButterKnife.inject(this);

        event = (Event) getIntent().getSerializableExtra(Extras.EVENT);

        setupListViewContent();
    }

    private void setupListViewContent() {
        //MenuAdapter menuAdapter = new MenuAdapter(this, R.layout.adapter_menu, shopCart.getShopCartItems());

        List<Item> items = getListItens();

        MenuListAdapter adapter = new MenuListAdapter(this, items);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(), "CLICKED", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Item> getListItens() {

        List<Item> result = new ArrayList<>();
        String type = event.getProducts().get(0).type;

        result.add(new Header(type));

        for (Product product : event.getProducts()) {
            if (type != product.type) {
                type = product.type;
                result.add(new Header(type));
            }

//            result.add(new ListItem(product, 0));
        }

        return result;
    }

    @OnClick(R.id.button_buy)
    public void paymentWithCard() {
        Intent intent = new Intent(this, PaymentActivity.class);
        startActivity(intent);
    }
}
