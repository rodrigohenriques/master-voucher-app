package br.com.mastervoucher.activities;

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
import br.com.mastervoucher.adapters.menulist.Item;
import br.com.mastervoucher.adapters.menulist.MenuListAdapter;
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
        //List<Item> items = ArrayList<Item>();
        //MenuListAdapter menuAdapter = new MenuListAdapter(this, items);
        //listView.setAdapter(menuAdapter);
    }

    @OnClick(R.id.button_confirm)
    public void confirmReceiver() {
        // TODO: request
    }

}
