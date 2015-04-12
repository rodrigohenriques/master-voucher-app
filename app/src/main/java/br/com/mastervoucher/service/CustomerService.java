package br.com.mastervoucher.service;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.ResponseHandlerInterface;

public class CustomerService {

    private static AsyncHttpClient client = new AsyncHttpClient();

    private static final String URL_CUSTOMER = "10.0.1.56:3000/customer";
    private static final String URL_CUSTOMER_ITEMS = URL_CUSTOMER + "/items";

    public void getItems(ResponseHandlerInterface responseHandler){
        client.get(URL_CUSTOMER_ITEMS, responseHandler);
    }
}
