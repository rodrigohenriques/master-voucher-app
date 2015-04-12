package br.com.mastervoucher.adapters.menulist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.mastervoucher.R;
import br.com.mastervoucher.models.DeliveryItem;
import br.com.mastervoucher.models.Product;

/**
 * Created by AlexandreMarones on 4/12/15.
 */
public class MyOrderAdapter extends ArrayAdapter<DeliveryItem> {

    private List<DeliveryItem> deliveryItems;
    private LayoutInflater inflater;
    private TextView textItemValue;
    private TextView textView;

    public MyOrderAdapter(Context context, List<DeliveryItem> deliveryItems) {
        super(context, 0, deliveryItems);
        inflater = LayoutInflater.from(context);
        this.deliveryItems = deliveryItems;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        final DeliveryItem deliveryItem = getDeliveredItem(position);

        Product product = deliveryItem.product;

        int layout = (product.unit != null && product.unit.length() > 0) ? R.layout.adapter_my_order : R.layout.adapter_title;

        rowView = inflater.inflate(layout, parent, false);

        if (product.unit != null && product.unit.length() > 0) {
            TextView textItemName = (TextView) rowView
                    .findViewById(R.id.text_name);
            textItemName.setText(deliveryItem.product.name);

            TextView textItemUnit = (TextView) rowView
                    .findViewById(R.id.text_unit);
            textItemUnit.setText(deliveryItem.product.unit);

            textItemValue = (TextView) rowView
                    .findViewById(R.id.text_amount);
            String amount = String.valueOf(deliveryItem.quantity);
            textItemValue.setText(amount);


            textView = (TextView) rowView.findViewById(R.id.text_selected_items);
            textView.setText(String.valueOf(deliveryItem.quantityDelivered));

        } else {
            TextView textTitle = (TextView) rowView
                    .findViewById(R.id.text_title);
            textTitle.setText(product.name);
        }

        return rowView;
    }

    @Override
    public int getCount() {
        return this.deliveryItems.size();
    }

    public DeliveryItem getDeliveredItem(int position) {
        return this.deliveryItems.get(position);
    }

    public void click(int position, View view) {

        DeliveryItem item = getItem(position - 1);

        item.addItem();

        reload(item, view);

    }

    private void reload(DeliveryItem deliveryItem, View view) {
        textItemValue = (TextView) view
                .findViewById(R.id.text_amount);
        textView = (TextView) view.findViewById(R.id.text_selected_items);
        String amount = String.valueOf(deliveryItem.quantity);
        textItemValue.setText(amount);
        textView.setText(String.valueOf(deliveryItem.quantityDelivered));

    }

    public void longClick(int position, View view) {
        DeliveryItem item = getItem(position - 1);

        item.removeAllItems();
        reload(item, view);
    }
}