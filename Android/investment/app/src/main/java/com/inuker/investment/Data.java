package com.inuker.investment;

/**
 * Created by liwentian on 2017/8/30.
 */

public class Data {

    public static final int YEAR = 0;
    public static final int CASH = 1;
    public static final int CASHS = 2;
    public static final int WIN = 3;
    public static final int WINS = 4;
    public static final int SUMS = 5;

    private int type;

    private String value;

    public Data(int type, String value) {
        this.type = type;
        this.value = value;
    }

    public Data(int type, int value) {
        this.type = type;
        this.value = String.valueOf(value);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
