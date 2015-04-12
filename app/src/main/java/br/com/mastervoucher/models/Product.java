package br.com.mastervoucher.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable {

    @SerializedName("name")
    public String name;

    @SerializedName("value")
    public String value;

    @SerializedName("unit")
    public String unit;

    @SerializedName("type")
    public String type;

    public Product() {
    }

    public Product(String name) {
        this.name = name;
    }

    public Product(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }

    public double getDoubleValue() {
        if (value == null) {
            return 0;
        } else {
            return Double.valueOf(value) / 100;
        }
    }
}
