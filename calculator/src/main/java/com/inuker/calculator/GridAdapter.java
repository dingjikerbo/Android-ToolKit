package com.inuker.calculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.inuker.calculator.Data.AGE;
import static com.inuker.calculator.Data.CASH;
import static com.inuker.calculator.Data.CASHS;
import static com.inuker.calculator.Data.WINS;
import static com.inuker.calculator.Data.YEAR;

/**
 * Created by liwentian on 2017/8/30.
 */

public class GridAdapter extends BaseAdapter {

    private List<Data> mDataList;

    /**
     * 初始本金
     */
    private int startCash = 100000;

    /**
     * 每年投资的本金增长比率
     */
    private float incRatio = 0.1f;

    /**
     * 投资年收益率
     */
    private float winRatio = 0.1f;

    /**
     * 投资元年
     */
    private int startYear = 2017;

    /**
     * 出生年
     */
    private int birthYear = 1988;

    /**
     * 多少年后结算
     */
    private int endYears = 30;

    /**
     * 持续投入本金多少年
     */
    private int cashYear = 20;

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
        mDataList.add(new Data(AGE, mContext.getString(R.string.age)));
        mDataList.add(new Data(CASH, mContext.getString(R.string.cash)));
        mDataList.add(new Data(WINS, mContext.getString(R.string.win)));
        mDataList.add(new Data(CASHS, mContext.getString(R.string.cashs)));
    }

    private void generateDataList() {
        mDataList.clear();

        generateTitle();

        int cash = startCash, cashSum = 0, sum = 0;

        for (int year = startYear; year < startYear + endYears; year++) {
            mDataList.add(new Data(YEAR, year));
            mDataList.add(new Data(AGE, year - birthYear));

            mDataList.add(new Data(CASH, cash));

            cashSum += cash;
            sum = (int) ((sum + cash) * (1 + winRatio));

            mDataList.add(new Data(WINS, sum - cashSum));
            mDataList.add(new Data(CASHS, sum));

            if (year - startYear > cashYear) {
                cash = 0;
            } else {
                cash *= 1 + incRatio;
            }
        }
    }

    public void setStartCash(int startCash) {
        this.startCash = startCash;
    }

    public void setIncRatio(float incRatio) {
        this.incRatio = incRatio;
    }

    public void setWinRatio(float winRatio) {
        this.winRatio = winRatio;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public void setEndAge(int endAge) {
        this.endYears = endAge;
    }

    public void setCachYear(int cachYear) {
        this.cashYear = cachYear;
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
