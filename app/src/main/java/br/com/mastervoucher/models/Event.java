package br.com.mastervoucher.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Isabel Porto on 11/04/2015.
 */
public class Event {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("products")
    private List<Product> products;


}
