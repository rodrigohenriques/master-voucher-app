package br.com.mastervoucher.service;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

public class DeliveryInfoService {

    private static AsyncHttpClient client = new AsyncHttpClient();
    private static final String URL_MERCHANT_DELIVERY = "http://10.0.1.56:3000/merchant/delivery";
    private static final String URL_MERCHANT_DELIVERY_CHECK = URL_MERCHANT_DELIVERY + "/check";

    public void delivery(Context context, String deliveryInfo, JsonHttpResponseHandler jsonHttpResponseHandler) throws UnsupportedEncodingException {
        StringEntity entity = new StringEntity(deliveryInfo);
        client.post(context, URL_MERCHANT_DELIVERY, entity, "application/json", jsonHttpResponseHandler);
    }

    public void checkDeliveryInfo(Context context, String deliveryInfo, JsonHttpResponseHandler jsonHttpResponseHandler) throws UnsupportedEncodingException {
        StringEntity entity = new StringEntity(deliveryInfo);
        client.post(context, URL_MERCHANT_DELIVERY_CHECK, entity, "application/json", jsonHttpResponseHandler);
    }
}
