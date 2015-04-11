package br.com.mastervoucher.models;

import java.io.Serializable;

/**
 * Created by Isabel Porto on 11/04/2015.
 */
public class ShopCartItem implements Serializable {
    public Product product;
    public int quantity;

    public double getTotalAmount(){
        double amount = product.getDoubleValue();
        return amount * quantity;
    }

}
