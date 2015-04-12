package br.com.mastervoucher.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.mastervoucher.models.Event;

/**
 * Created by Isabel Porto on 12/04/2015.
 */
public class JSONUtil {

    private Gson gson;

    public JSONUtil() {
        this.gson = new GsonBuilder().disableHtmlEscaping().create();
    }

    public <T> T from(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public String to(Object obj) {
        return gson.toJson(obj);
    }
}
