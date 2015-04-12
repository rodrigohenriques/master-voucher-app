package br.com.mastervoucher.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonHelper {

    private static Gson gson;

    static {
        gson = new GsonBuilder().disableHtmlEscaping().create();
    }

    public <T> T from(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public String to(Object obj) {
        return gson.toJson(obj);
    }
}
