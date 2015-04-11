package br.com.mastervoucher.service;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

/**
 * Created by Isabel Porto on 11/04/2015.
 */
public class EventService {

    private static AsyncHttpClient client = new AsyncHttpClient();
    private static final String EVENT_URL = "http://10.0.1.56:3000/event/";

    public void getEvent(String eventId,ResponseHandlerInterface responseHandler){
        client.get(EVENT_URL + eventId, responseHandler);
    }
}
