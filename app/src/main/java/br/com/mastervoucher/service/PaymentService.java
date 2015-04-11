package br.com.mastervoucher.service;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by Isabel Porto on 11/04/2015.
 */
public class PaymentService {

    private static AsyncHttpClient client = new AsyncHttpClient();
    private static final String PAYMENT_URL = "http://10.0.1.56:3000/payment/pay";

    public void pay(String tokenId, String amount, AsyncHttpResponseHandler responseHandler){
        RequestParams params = new RequestParams();
        params.add("tokenId", tokenId);
        params.add("amount", amount );
        client.post(PAYMENT_URL, params, responseHandler);
    }
}
