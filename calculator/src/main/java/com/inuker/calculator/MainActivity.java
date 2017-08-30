package com.inuker.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;

public class MainActivity extends Activity {

    private GridView mGridView;

    private GridAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGridView = (GridView) findViewById(R.id.grid);
        mAdapter = new GridAdapter(this);
        mGridView.setAdapter(mAdapter);

        final EditText et1 = (EditText) findViewById(R.id.first_cash);
        final Spinner et2 = (Spinner) findViewById(R.id.cash_year);
        final Spinner et3 = (Spinner) findViewById(R.id.inc_ratio);
        final Spinner et4 = (Spinner) findViewById(R.id.all_year);
        final Spinner et5 = (Spinner) findViewById(R.id.win_ratio);

        et2.setSelection(1);
        et4.setSelection(5);
        et5.setSelection(2);

        et1.setText(mAdapter.getFirstCash() + "");

        final String[] strs = getResources().getStringArray(R.array.inc_ratio);
        final String[] years = getResources().getStringArray(R.array.year);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mAdapter.setFirstCash(Integer.parseInt(et1.getText().toString()));

                String s = years[et2.getSelectedItemPosition()];
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

        mAdapter.notifyDataSetChanged();
    }
}
