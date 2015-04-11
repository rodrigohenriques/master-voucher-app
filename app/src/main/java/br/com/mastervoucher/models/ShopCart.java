package br.com.mastervoucher.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Isabel Porto on 11/04/2015.
 */
public class ShopCart implements Serializable {
    private List<ShopCartItem> shopCartItems;

    public ShopCart() {
        this.shopCartItems = new ArrayList<>();
    }

    public void addShopCardItem(ShopCartItem shopCartItem) {
        shopCartItems.add(shopCartItem);
    }

    public List<ShopCartItem> getShopCartItems() {
        return shopCartItems;
    }

    public String getTotalAmount() {
        Float totalAmount = 0f;
        for (ShopCartItem shopCardItem : shopCartItems) {
            totalAmount += shopCardItem.getTotalAmount();
        }
        return String.valueOf(totalAmount);
    }
}