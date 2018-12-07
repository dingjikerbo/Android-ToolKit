package com.inuker.investment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.inuker.investment.Data.CASH;
import static com.inuker.investment.Data.CASHS;
import static com.inuker.investment.Data.SUMS;
import static com.inuker.investment.Data.WIN;
import static com.inuker.investment.Data.WINS;
import static com.inuker.investment.Data.YEAR;

/**
 * Created by liwentian on 2017/8/30.
 */

public class GridAdapter extends BaseAdapter {

    private List<Data> mDataList;

    /**
     * 初始本金
     */
    private int firstCash = 100000;

    /**
     * 每年投资的本金增长比率
     */
    private float incRatio = 0.0f;

    /**
     * 投资年收益率
     */
    private float winRatio = 0.0f;

    /**
     * 多少年后结算
     */
    private int allYears = 20;

    /**
     * 持续投入本金多少年
     */
    private int cashYears = 10;

    private Context mContext;

    public GridAdapter(Context context) {
        mContext = context;
        mDataList = new ArrayList<>();
    }

    @Override
    public void notifyDataSetChanged() {
        generateDataList();
        super.notifyDataSetChanged();
    }

    private void generateTitle() {
        mDataList.add(new Data(YEAR, mContext.getString(R.string.year)));
        mDataList.add(new Data(CASH, mContext.getString(R.string.cash)));
        mDataList.add(new Data(CASHS, mContext.getString(R.string.cashs)));
        mDataList.add(new Data(WIN, mContext.getString(R.string.win)));
        mDataList.add(new Data(WINS, mContext.getString(R.string.wins)));
        mDataList.add(new Data(SUMS, mContext.getString(R.string.sums)));
    }

    private void generateDataList() {
        mDataList.clear();

        generateTitle();

        int cash = firstCash; // 本金
        int cashSum = 0; // 本金和
        int sum = 0; // 本息总和

        for (int year = 1; year <= allYears; year++) {
            mDataList.add(new Data(YEAR, year));
            mDataList.add(new Data(CASH, cash));

            cashSum += cash;
            mDataList.add(new Data(CASHS, cashSum));

            int next_sum = (int) ((sum + cash) * (1 + winRatio));

            mDataList.add(new Data(WIN, next_sum - sum - cash));
            mDataList.add(new Data(WINS, next_sum - cashSum));
            mDataList.add(new Data(CASHS, next_sum));

            sum = next_sum;

            if (year >= cashYears) {
                cash = 0;
            } else {
                cash *= 1 + incRatio;
            }
        }
    }

    public void setFirstCash(int firstCash) {
        this.firstCash = firstCash;
    }

    public void setIncRatio(float incRatio) {
        this.incRatio = incRatio;
    }

    public void setWinRatio(float winRatio) {
        this.winRatio = winRatio;
    }

    public float getIncRatio() {
        return incRatio;
    }

    public float getWinRatio() {
        return winRatio;
    }

    public int getAllYears() {
        return allYears;
    }

    public void setAllYears(int allYears) {
        this.allYears = allYears;
    }

    public int getCashYears() {
        return cashYears;
    }

    public void setCashYears(int cashYears) {
        this.cashYears = cashYears;
    }

    public int getFirstCash() {
        return firstCash;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid, null);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.text);

        Data data = (Data) getItem(position);

        tv.setText(data.getValue() + "");

        return convertView;
    }
}
