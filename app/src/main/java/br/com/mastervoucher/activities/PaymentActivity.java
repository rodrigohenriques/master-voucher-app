package br.com.mastervoucher.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.simplify.android.sdk.Simplify;
import com.simplify.android.sdk.model.Card;
import com.simplify.android.sdk.model.SimplifyError;
import com.simplify.android.sdk.model.Token;
import com.simplify.android.sdk.view.CardEditor;

import org.apache.http.Header;

import br.com.mastervoucher.R;
import br.com.mastervoucher.models.ShopCart;
import br.com.mastervoucher.service.PaymentService;

public class PaymentActivity extends BaseActivity {

    private static final String SIMPLIFY_KEY = "sbpb_NDhiODIyZTQtNzM4OC00YmRlLTliMzEtYjBmZWE5NjA5NDVh";
    public static final String SHOP_CART_ITEM = "SHOP_CART_ITEM";
    private static final String TAG = PaymentActivity.class.getSimpleName();
    private Simplify mSimplify;
    private CardEditor mCardEditor;
    private ShopCart shopCart;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); progress = new ProgressDialog(this);
        setContentView(R.layout.activity_payment);

        mSimplify = new Simplify(SIMPLIFY_KEY);
        progress = new ProgressDialog(this);
        shopCart = (ShopCart) getIntent().getSerializableExtra(SHOP_CART_ITEM);

        getSupportActionBar().setTitle("Pagamento");

        initUI();
    }

    private void initUI() {
        mCardEditor = (CardEditor) findViewById(R.id.card_editor);
        mCardEditor.setAmount(shopCart.getDoubleTotalAmount());


        mCardEditor.setOnChargeClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hideKeyboard(view);
                progress.setMessage("Realizando Pagamento...");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.show();

                Card card = mCardEditor.getCard();
                AsyncTask<?, ?, ?> task = mSimplify.createCardToken(card, new Simplify.CreateTokenListener() {
                    @Override
                    public void onSuccess(Token token) {
                        Log.i(TAG, "Created Token: " + token.getId());
                        PaymentService paymentService = new PaymentService();
                        AsyncHttpResponseHandler responseHandler = getPaymentAsyncHttpResponseHandler();
                        paymentService.pay(token.getId(), shopCart.getTotalAmount(), shopCart.getShopCartItems(), responseHandler);
                    }

                    @Override
                    public void onError(SimplifyError error) {
                        //TODO:
                        Log.e(TAG, "Error Creating Token: " + error.getMessage());
                        //mCardEditor.showErrorOverlay("Unable to retrieve card token. " + error.getMessage());
                    }
                });
            }
        });

    }

    private AsyncHttpResponseHandler getPaymentAsyncHttpResponseHandler() {
        return new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                progress.hide();
                Log.i(TAG, "Pagou sucesso");
                Intent intent = new Intent(PaymentActivity.this, MyOrdersActivity.class);
                startActivity(intent);

                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e(TAG, "Pagou erro");
                progress.hide();
                //TODO:erro
            }
        };
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
