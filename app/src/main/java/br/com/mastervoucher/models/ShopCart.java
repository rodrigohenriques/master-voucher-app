package br.com.mastervoucher.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.mastervoucher.util.AppType;

public class ShopCart implements Serializable {
    private List<ShopCartItem> shopCartItems;

    public ShopCart() {
        shopCartItems = new ArrayList<>();
    }

    public ShopCart( List<ShopCartItem> shopCartItems) {
        this.shopCartItems = shopCartItems;
    }

    public List<ShopCartItem> getShopCartItems() {
        return shopCartItems;
    }

    public String getTotalAmount() {
        double totalAmount = getDoubleTotalAmount();
        String value = String.format("%.2f", totalAmount);
        value = value.replaceAll("\\.", "");
        value = value.replaceAll(",", "");
        return value;
    }

    public double getDoubleTotalAmount() {
        double totalAmount = 0;
        for (ShopCartItem shopCardItem : shopCartItems) {
            totalAmount += shopCardItem.getTotalAmount();
        }
        return totalAmount;
    }

    public void add(ShopCartItem item) {
        shopCartItems.add(item);
    }

}
