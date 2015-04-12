package br.com.mastervoucher.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Isabel Porto on 11/04/2015.
 */
public class Event implements Serializable {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("products")
    private ArrayList<Product> products;

    public ArrayList<Product> getProducts() {
        return products;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
