package br.com.mastervoucher.activities;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.mastervoucher.R;
import br.com.mastervoucher.adapters.OrderAdapter;
import br.com.mastervoucher.models.DeliveredItem;
import br.com.mastervoucher.util.Extras;
import butterknife.InjectView;
import butterknife.OnClick;

public class OrderActivity extends BaseActivity {

    @InjectView(R.id.listview)
    ListView listView;

    List<DeliveredItem> deliveredItems = new ArrayList<DeliveredItem>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        deliveredItems = (List<DeliveredItem>) getIntent().getExtras().getSerializable(Extras.ORDER_LIST);
    }

    private void setupListViewContent() {
        OrderAdapter menuAdapter = new OrderAdapter(this, deliveredItems);
        listView.setAdapter(menuAdapter);
    }

    @OnClick(R.id.button_confirm)
    public void confirmReceiver() {
        // TODO: request
    }

}
