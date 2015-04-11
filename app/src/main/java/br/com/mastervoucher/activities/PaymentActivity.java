package br.com.mastervoucher.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;


import com.loopj.android.http.AsyncHttpResponseHandler;
import com.simplify.android.sdk.Simplify;
import com.simplify.android.sdk.model.Card;
import com.simplify.android.sdk.model.SimplifyError;
import com.simplify.android.sdk.model.Token;
import com.simplify.android.sdk.view.CardEditor;

import org.apache.http.Header;

import br.com.mastervoucher.R;
import br.com.mastervoucher.service.PaymentService;

public class PaymentActivity extends ActionBarActivity {

    private static final String SIMPLIFY_KEY = "sbpb_NDhiODIyZTQtNzM4OC00YmRlLTliMzEtYjBmZWE5NjA5NDVh";
    private static final String TAG = PaymentActivity.class.getSimpleName();
    private Simplify mSimplify;
    private CardEditor mCardEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mSimplify = new Simplify(SIMPLIFY_KEY);
        initUI();
    }

    private void initUI() {
        mCardEditor = (CardEditor) findViewById(R.id.card_editor);

        mCardEditor.setOnChargeClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);

                Card card = mCardEditor.getCard();
                AsyncTask<?, ?, ?> task = mSimplify.createCardToken(card, new Simplify.CreateTokenListener() {
                    @Override
                    public void onSuccess(Token token) {
                        Log.i(TAG, "Created Token: " + token.getId());
                        PaymentService paymentService = new PaymentService();
                        AsyncHttpResponseHandler responseHandler = getPaymentAsyncHttpResponseHandler();
                        paymentService.pay(token.getId(), "500", responseHandler);
                    }

                    @Override
                    public void onError(SimplifyError error) {
                        //TODO:
                        Log.e(TAG, "Error Creating Token: " + error.getMessage());
                        mCardEditor.showErrorOverlay("Unable to retrieve card token. " + error.getMessage());
                    }
                });
            }
        });

        // init reset button
        ((Button) findViewById(R.id.btnReset)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCardEditor.reset();
            }
        });
    }

    private AsyncHttpResponseHandler getPaymentAsyncHttpResponseHandler() {
        return new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i(TAG, "Pagou sucesso");
                //TODO: Sucesso..
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e(TAG, "Pagou erro");
                //TODO:erro
            }
        };
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
