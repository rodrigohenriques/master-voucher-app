package br.com.mastervoucher.adapters.menulist;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import br.com.mastervoucher.R;
import br.com.mastervoucher.models.ShopCartItem;

public class ListItem implements Item {
    private ShopCartItem shopCartItem;

    public ListItem(ShopCartItem shopCartItem) {

        this.shopCartItem = shopCartItem;
    }

    @Override
    public int getViewType() {
        return MenuListAdapter.RowType.LIST_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {

        int layout = R.layout.adapter_menu;

        convertView = inflater.inflate(layout, null);

        TextView textAmount = (TextView) convertView
                .findViewById(R.id.text_amount);
        String amount = String.format("%.2f", shopCartItem.getTotalAmount());
        textAmount.setText(amount);

        TextView textItemName = (TextView) convertView
                .findViewById(R.id.text_name);
        textItemName.setText(shopCartItem.product.name);

        TextView textItemUnit = (TextView) convertView
                .findViewById(R.id.text_unit);
        textItemUnit.setText(shopCartItem.product.unit);

        TextView textItemValue = (TextView) convertView
                .findViewById(R.id.text_value);

        String value = String.format("R$ %.2f", shopCartItem.product.getDoubleValue());
        textItemValue.setText(value);

        return convertView;
    }

}
