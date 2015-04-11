package br.com.mastervoucher.models;

import java.io.Serializable;

/**
 * Created by Isabel Porto on 11/04/2015.
 */
public class ShopCartItem implements Serializable {
    private Product product;
    private int quantity;

    public float getTotalAmount(){

        Float amount = product.getFloatValue();
        return amount * quantity;
    }

}
