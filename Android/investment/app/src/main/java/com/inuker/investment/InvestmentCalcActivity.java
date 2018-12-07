package com.inuker.investment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.Spinner;

public class InvestmentCalcActivity extends Activity {

    private GridView mGridView;

    private GridAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invest_calc);

        mGridView = findViewById(R.id.grid);
        mAdapter = new GridAdapter(this);
        mGridView.setAdapter(mAdapter);

        final Spinner et1 = findViewById(R.id.first_cash);
        final Spinner et2 = findViewById(R.id.cash_year);
        final Spinner et3 = findViewById(R.id.inc_ratio);
        final Spinner et4 = findViewById(R.id.all_year);
        final Spinner et5 = findViewById(R.id.win_ratio);

        et1.setSelection(2);
        et2.setSelection(1);
        et4.setSelection(5);
        et5.setSelection(2);

        final String[] first_cashs = getResources().getStringArray(R.array.first_cash);
        final String[] strs = getResources().getStringArray(R.array.inc_ratio);
        final String[] years = getResources().getStringArray(R.array.year);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String s = first_cashs[et1.getSelectedItemPosition()];
                mAdapter.setFirstCash(Integer.parseInt(s));

                s = years[et2.getSelectedItemPosition()];
                mAdapter.setCashYears(Integer.parseInt(s));

                s = strs[et3.getSelectedItemPosition()];
                s = s.substring(0, s.length() - 1);
                mAdapter.setIncRatio(0.01f * Integer.parseInt(s));

                s = years[et4.getSelectedItemPosition()];
                mAdapter.setAllYears(Integer.parseInt(s));

                s = strs[et5.getSelectedItemPosition()];
                s = s.substring(0, s.length() - 1);
                mAdapter.setWinRatio(0.01f * Integer.parseInt(s));

                mAdapter.notifyDataSetChanged();
            }
        });

//        mAdapter.notifyDataSetChanged();
    }
}
