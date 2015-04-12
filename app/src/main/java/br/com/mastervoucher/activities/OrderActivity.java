package br.com.mastervoucher.activities;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.mastervoucher.R;
import br.com.mastervoucher.adapters.OrderAdapter;
import br.com.mastervoucher.models.DeliveryItem;
import butterknife.InjectView;
import butterknife.OnClick;

public class OrderActivity extends BaseActivity {

    @InjectView(R.id.listview)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
    }

    private void setupListViewContent() {
        List<DeliveryItem> items = new ArrayList< DeliveryItem>();
        OrderAdapter menuAdapter = new OrderAdapter(this, items);
        listView.setAdapter(menuAdapter);
    }

    @OnClick(R.id.button_confirm)
    public void confirmReceiver() {
        // TODO: request
    }

}
