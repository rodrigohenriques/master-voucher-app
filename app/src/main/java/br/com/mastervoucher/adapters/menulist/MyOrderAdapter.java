package br.com.mastervoucher.adapters.menulist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.mastervoucher.R;
import br.com.mastervoucher.models.Product;
import br.com.mastervoucher.models.ShopCartItem;

/**
 * Created by AlexandreMarones on 4/12/15.
 */
public class MyOrderAdapter extends ArrayAdapter<ShopCartItem> {

    private List<ShopCartItem> datasource;
    private LayoutInflater inflater;

    public MyOrderAdapter(Context context, List<ShopCartItem> items) {
        super(context, 0, items);
        inflater = LayoutInflater.from(context);
        datasource = items;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        final ShopCartItem shopCartItem = getShopCartItem(position);

        Product product = shopCartItem.product;

        int layout = (product.unit != null && product.unit.length() > 0) ? R.layout.adapter_my_order : R.layout.adapter_title;

        rowView = inflater.inflate(layout, parent, false);

        if (product.unit != null && product.unit.length() > 0) {
            TextView textItemName = (TextView) rowView
                    .findViewById(R.id.text_name);
            textItemName.setText(shopCartItem.product.name);

            TextView textItemUnit = (TextView) rowView
                    .findViewById(R.id.text_unit);
            textItemUnit.setText(shopCartItem.product.unit);

            TextView textItemValue = (TextView) rowView
                    .findViewById(R.id.text_amount);
            String amount = String.valueOf(shopCartItem.quantity);
            textItemValue.setText(amount);
        } else {
            TextView textTitle = (TextView) rowView
                    .findViewById(R.id.text_title);
            textTitle.setText(product.name);
        }

        return rowView;
    }

    @Override
    public int getCount() {
        return this.datasource.size();
    }

    public ShopCartItem getShopCartItem(int position) {
        return this.datasource.get(position);
    }
}
