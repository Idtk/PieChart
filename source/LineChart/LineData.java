package com.example.administrator.customview.LineChart;

/**
 * Created by DoBest on 2016/4/22.
 */
public class LineData {

    private String name;
    private float[][] value;
    private int color;

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setValue(float[][] value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public float[][] getValue() {
        return value;
    }
}
