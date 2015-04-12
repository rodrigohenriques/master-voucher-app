package br.com.mastervoucher.models;

import java.io.Serializable;
import java.util.List;

public class ShopCart implements Serializable {
    private List<ShopCartItem> shopCartItems;

    public ShopCart() {

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
