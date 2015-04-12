package br.com.mastervoucher.service;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.List;

import br.com.mastervoucher.adapters.menulist.ListItem;
import br.com.mastervoucher.models.ShopCartItem;
import br.com.mastervoucher.util.JSONUtil;

/**
 * Created by Isabel Porto on 11/04/2015.
 */
public class PaymentService {

    private static AsyncHttpClient client = new AsyncHttpClient();
    private static final String PAYMENT_URL = "http://10.0.1.56:3000/payment/pay";

    public void pay(String tokenId, String amount, List<ShopCartItem> shopCartItems, AsyncHttpResponseHandler responseHandler){
        RequestParams params = new RequestParams();
        params.add("tokenId", tokenId);
        params.add("amount", amount );
        JSONUtil jsonUtil = new JSONUtil();
        params.add("items", jsonUtil.to(shopCartItems));
        client.post(PAYMENT_URL, params, responseHandler);
    }
}
