package br.com.mastervoucher.adapters.menulist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class MenuListAdapter extends ArrayAdapter<Item> {
    private LayoutInflater mInflater;

    public enum RowType {
        LIST_ITEM, HEADER_ITEM
    }

    public MenuListAdapter(Context context, List<Item> items) {
        super(context, 0, items);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getViewTypeCount() {
        return RowType.values().length;

    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getViewType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItem(position).getView(mInflater, convertView);
    }

    public void click(int position) {

        Item item = getItem(position);

        if ( item instanceof ListItem ) {

            ListItem listItem = (ListItem) item;

            listItem.plus();
        }
    }

    public void longClick(int position) {

        Item item = getItem(position);

        if ( item instanceof ListItem ) {

            ListItem listItem = (ListItem) item;

            listItem.clear();
        }
    }
}
