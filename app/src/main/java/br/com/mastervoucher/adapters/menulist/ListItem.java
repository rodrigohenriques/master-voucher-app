package br.com.mastervoucher.adapters.menulist;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import br.com.mastervoucher.R;
import br.com.mastervoucher.models.Product;
import br.com.mastervoucher.models.ShopCartItem;

public class ListItem implements Item {
    private ShopCartItem shopCartItem;
    private TextView textAmount;
    private TextView textItemName;
    private TextView textItemUnit;
    private TextView textItemValue;

    public ListItem(Product product) {

        this.shopCartItem = new ShopCartItem(product, 0);
    }

    public void plus() {
        shopCartItem.addOne();
        textAmount.setText(shopCartItem.getQuantity());
    }

    public void clear() {
        shopCartItem.clear();
        textAmount.setText(shopCartItem.getQuantity());
    }

    @Override
    public int getViewType() {
        return MenuListAdapter.RowType.LIST_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {

        int layout = R.layout.adapter_menu;

        convertView = inflater.inflate(layout, null);

        textAmount = (TextView) convertView
                .findViewById(R.id.text_amount);
        textAmount.setText(shopCartItem.getQuantity());

        textItemName = (TextView) convertView
                .findViewById(R.id.text_name);
        textItemName.setText(shopCartItem.product.name);

        textItemUnit = (TextView) convertView
                .findViewById(R.id.text_unit);
        textItemUnit.setText(shopCartItem.product.unit);

        textItemValue = (TextView) convertView
                .findViewById(R.id.text_value);

        String value = String.format("R$ %.2f", shopCartItem.product.getDoubleValue());
        textItemValue.setText(value);

        return convertView;
    }

}
