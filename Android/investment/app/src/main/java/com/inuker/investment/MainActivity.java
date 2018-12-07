package com.inuker.investment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button mBtnInvest;
    private Button mBtnStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnInvest = findViewById(R.id.invest);
        mBtnStock = findViewById(R.id.stock);

        mBtnStock.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                start(StockCalcActivity.class);
            }
        });

        mBtnInvest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                start(InvestmentCalcActivity.class);
            }
        });
    }

    private void start(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}
