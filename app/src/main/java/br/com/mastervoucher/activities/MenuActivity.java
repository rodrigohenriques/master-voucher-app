package br.com.mastervoucher.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.mastervoucher.R;
import br.com.mastervoucher.adapters.menulist.Header;
import br.com.mastervoucher.adapters.menulist.Item;
import br.com.mastervoucher.adapters.menulist.ListItem;
import br.com.mastervoucher.adapters.menulist.MenuListAdapter;
import br.com.mastervoucher.dao.EventDAO;
import br.com.mastervoucher.models.Event;
import br.com.mastervoucher.models.Product;
import br.com.mastervoucher.models.ShopCart;
import br.com.mastervoucher.models.ShopCartItem;
import br.com.mastervoucher.service.EventService;
import br.com.mastervoucher.util.JSONUtil;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MenuActivity extends BaseActivity {

    @InjectView(R.id.listview)
    ListView listView;
    @InjectView(R.id.text_total_value)
    TextView totalAmountTextView;

    private Event event;
    private MenuListAdapter menuAdapter;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ButterKnife.inject(this);

        progress = new ProgressDialog(this);
        EventDAO dao = new EventDAO(this);

        if (dao.hasEventId()) {
            getEvent(dao.getEventId());
        } else {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void setupListViewContent() {
        View headerView = LayoutInflater.from(this).inflate(
                R.layout.header_view_event, null);
        TextView textEventName = (TextView) headerView.findViewById(R.id.text_event_name);
        textEventName.setText(event.getName());

        listView.addHeaderView(headerView, null, false);

        List<Item> items = getListItens();

        menuAdapter = new MenuListAdapter(this, items);

        listView.setAdapter(menuAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                menuAdapter.click(position);
                atualizaTotal();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                menuAdapter.longClick(position);
                atualizaTotal();

                return true;
            }
        });
    }

    private void atualizaTotal() {
        ShopCart shopCart = getShopCart();

        String value = String.format("R$ %.2f",  shopCart.getDoubleTotalAmount());
        totalAmountTextView.setText(value);
    }

    private List<Item> getListItens() {

        List<Item> result = new ArrayList<>();
        String type = "";

        for (Product product : event.getProducts()) {
            if (!type.equals(product.type)) {
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
        intent.putExtra(PaymentActivity.SHOP_CART_ITEM,new ShopCart(getShopCartItems()) );
        startActivity(intent);
    }


    private ShopCart getShopCart() {
        return new ShopCart(getShopCartItems());
    }

    private List<ShopCartItem> getShopCartItems() {
        List<ShopCartItem> shopCartItems = new ArrayList<ShopCartItem>();
        for(Item item:menuAdapter.getItems()){
            if ( item instanceof ListItem ) {

                ListItem listItem = (ListItem) item;
                shopCartItems.add(listItem.getShopCartItem());
            }
        }
        return shopCartItems;
    }

    private void getEvent(String eventId) {


        EventService eventService = new EventService();

        progress.setMessage("Atualizando...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        eventService.getEvent(eventId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                JSONUtil jsonUtil = new JSONUtil();
                event = jsonUtil.from(response.toString(), Event.class);
                EventDAO dao = new EventDAO(MenuActivity.this);
                dao.saveEventName(event.getName());
                setupListViewContent();
                progress.hide();


            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(getApplicationContext(), "Erro ao buscar o evento", Toast.LENGTH_SHORT).show();
                finish();

                progress.hide();
            }
        });

    }
}
