package br.com.mastervoucher.activities;

import android.os.Bundle;
import android.widget.ListView;

import br.com.mastervoucher.R;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by AlexandreMarones on 4/11/15.
 */

public class MenuActivity extends BaseActivity {

    @InjectView(R.id.listview)
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ButterKnife.inject(this);

        setupListViewContent();
    }

    private void setupListViewContent() {


    }

    @OnClick(R.id.button_pay)
    public void paymentWithCard() {

    }

    @OnClick(R.id.button_masterpass)
    public void paymentWithMasterPass() {

    }
}
