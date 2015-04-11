package br.com.mastervoucher.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Isabel Porto on 11/04/2015.
 */
public class Product implements Serializable {

    @SerializedName("name")
    public String name;
    @SerializedName("value")
    public String value;
    @SerializedName("unit")
    public String unit;

    public double getDoubleValue(){
        if(value == null){
            return 0;
        }else{
            return Float.valueOf(value)/100;
        }
    }


}
