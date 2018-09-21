package com.inuker.stock;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private static final String INVALID = "#####";

    private LinearLayout mContainer;
    private Button mBtnAdd, mBtnDel;

//    private TextView mTvLine1;
//    private TextView mTvLine2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContainer = findViewById(R.id.container);
        mBtnAdd = findViewById(R.id.add);
        mBtnDel = findViewById(R.id.del);
//        mTvLine1 = findViewById(R.id.line1);
//        mTvLine2 = findViewById(R.id.line2);

        mBtnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                add();
            }
        });

        mBtnDel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                del();
            }
        });

        add();
    }

    private boolean checkComplete(ViewHolder holder) {
        if (getPrice(holder) <= 0) {
            return false;
        }
        if (getCount(holder) == 0) {
            return false;
        }
        return true;
    }

    private void add() {
        if (mContainer.getChildCount() > 0) {
            ViewHolder holder = (ViewHolder) mContainer.getChildAt(mContainer.getChildCount() - 1).getTag();
            if (!checkComplete(holder)) {
                Toast.makeText(this, R.string.line_uncomplete, Toast.LENGTH_SHORT).show();
                return;
            }
        }
        addLine();
    }

    private void del() {
        if (mContainer.getChildCount() > 0) {
            mContainer.removeViewAt(mContainer.getChildCount() - 1);
            if (mContainer.getChildCount() > 0) {
                ViewHolder holder = (ViewHolder) mContainer.getChildAt(mContainer.getChildCount() - 1).getTag();
                holder.price.requestFocus();
            }
        }
    }

    private class ViewHolder {
        EditText price;
        EditText count;
        TextView result;
    }

    private void addLine() {
        final View view = LayoutInflater.from(this).inflate(R.layout.line, null);

        mContainer.addView(view, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dp2px(100)
        ));

        ViewHolder holder = new ViewHolder();
        holder.price = view.findViewById(R.id.price);
        holder.count = view.findViewById(R.id.count);
        holder.result = view.findViewById(R.id.result);
        view.setTag(holder);

        final EditText etPrice = holder.price;
        etPrice.addTextChangedListener(new InputListener() {
            @Override
            void onTextChanged(String s) {
                recalcAllLines();
            }
        });

        final EditText etCount = holder.count;
        etCount.addTextChangedListener(new InputListener() {
            @Override
            void onTextChanged(String s) {
                recalcAllLines();
            }
        });

        etPrice.requestFocus();
    }

    private void recalcAllLines() {
        /**
         * 持有的股票总价值
         */
        float allStock = 0f;

        /**
         * 持有的股票股数
         */
        int allCount = 0;

        /**
         * 投入的总资金数
         */
        float allMoney = 0f;

        /**
         * 现金余额
         */
        float leftMoney = 0f;

        /**
         * 盈利
         */
        int gain = 0;

        for (int i = 0; i < mContainer.getChildCount(); i++) {
            ViewHolder holder = (ViewHolder) mContainer.getChildAt(i).getTag();
            float price = getPrice(holder);
            int count = getCount(holder);

            if (price <= 0 || count == 0) {
                allStock = -1;
            }

            if (allStock < 0 || allCount + count <= 0) {
                holder.result.setText(INVALID);
                continue;
            }

            allStock += price * count;
            allCount += count;

            if (count > 0) {
                // 买入
                if (price * count > leftMoney) {
                    allMoney += price * count - leftMoney;
                    leftMoney = 0;
                } else {
                    leftMoney -= price * count;
                }
            } else {
                // 卖出
                leftMoney += -price * count;
            }

            gain = (int) (allCount * price + leftMoney - allMoney);

            float base = allStock / allCount;
            holder.result.setText(getString(R.string.result_desc, allMoney, leftMoney, allCount, base, gain));
        }
    }

    private float getPrice(ViewHolder holder) {
        String s = holder.price.getText().toString().trim();
        if (TextUtils.isEmpty(s)) {
            return 0;
        }
        try {
            float f = Float.parseFloat(s);
            if (f > 0) {
                return f;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int getCount(ViewHolder holder) {
        String s = holder.count.getText().toString().trim();
        if (TextUtils.isEmpty(s)) {
            return 0;
        }
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int dp2px(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    private abstract class InputListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            onTextChanged(s.toString().trim());
        }

        abstract void onTextChanged(String s);
    }
}
