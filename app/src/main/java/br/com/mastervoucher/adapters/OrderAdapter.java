package br.com.mastervoucher.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.mastervoucher.R;
import br.com.mastervoucher.models.DeliveryItem;
import br.com.mastervoucher.models.ShopCartItem;

/**
 * Created by AlexandreMarones on 4/12/15.
 */
public class OrderAdapter extends ArrayAdapter<DeliveryItem> {

    private List<DeliveryItem> datasource;
    private LayoutInflater inflater;
    private Activity ownerActivity;

    public OrderAdapter(Context context, List<DeliveryItem> objects) {
        super(context, 0, objects);

        this.inflater = LayoutInflater.from(context);
        this.datasource = objects;
        this.ownerActivity = (Activity) context;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        final ShopCartItem shopCartItem = getShopCartItem(position);

        int layout = R.layout.adapter_menu;

        rowView = inflater.inflate(layout, parent, false);

        TextView textAmount = (TextView) rowView
                .findViewById(R.id.text_amount);
        String amount = String.format("%.2f", shopCartItem.getTotalAmount());
        textAmount.setText(amount);

        TextView textItemName = (TextView) rowView
                .findViewById(R.id.text_name);
        textItemName.setText(shopCartItem.product.name);

        TextView textItemUnit = (TextView) rowView
                .findViewById(R.id.text_unit);
        textItemUnit.setText(shopCartItem.product.unit);

        TextView textItemValue = (TextView) rowView
                .findViewById(R.id.text_name);
        String value = String.format("R$ %.2f", shopCartItem.product.getDoubleValue());
        textItemValue.setText(value);

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
