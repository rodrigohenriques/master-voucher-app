package br.com.mastervoucher.models;

import java.io.Serializable;

/**
 * Created by Isabel Porto on 11/04/2015.
 */
public class DeliveryItem extends ShopCartItem {

    public int quantityDelivered;

    public DeliveryItem(ShopCartItem shopCartItem) {
        super(shopCartItem.product, shopCartItem.quantity);
    }

    public void addItem(){
        if(quantity>0) {
            quantity--;
            quantityDelivered++;
        }
    }

    public void removeAllItems(){
        quantity = quantity + quantityDelivered;
        quantityDelivered = 0;
    }

}
