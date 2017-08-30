package com.inuker.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class MainActivity extends Activity {

    private GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGridView = (GridView) findViewById(R.id.grid);
        GridAdapter adapter = new GridAdapter(this);
        mGridView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }
}
