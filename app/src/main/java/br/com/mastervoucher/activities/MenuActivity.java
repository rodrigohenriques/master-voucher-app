package br.com.mastervoucher.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.mastervoucher.R;
import br.com.mastervoucher.adapters.menulist.Header;
import br.com.mastervoucher.adapters.menulist.Item;
import br.com.mastervoucher.adapters.menulist.ListItem;
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
    ListView listView;

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
        View headerView = LayoutInflater.from(this).inflate(
                R.layout.header_view_event, null);
        ImageView logo = (ImageView) headerView.findViewById(R.id.image_event_logo);
        // TODO: set image and text for event name
        TextView textEventName = (TextView) headerView.findViewById(R.id.text_event_name);
        textEventName.setText(event.getName());

        listView.addHeaderView(headerView, null, false);

        List<Item> items = getListItens();

        final MenuListAdapter menuAdapter = new MenuListAdapter(this, items);

        listView.setAdapter(menuAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                menuAdapter.click(position);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                menuAdapter.longClick(position);

                return true;
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

            result.add(new ListItem(product));
        }

        return result;
    }

    @OnClick(R.id.button_buy)
    public void paymentWithCard() {
        Intent intent = new Intent(this, PaymentActivity.class);
        startActivity(intent);
    }
}
