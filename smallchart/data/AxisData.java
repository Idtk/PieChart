package com.idtk.smallchart.data;

import com.idtk.smallchart.interfaces.iData.IAxisData;

/**
 * Created by Idtk on 2016/6/6.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 坐标轴数据基类
 */
public class AxisData extends BaseData implements IAxisData{

    protected float axisLength;
    protected float maximum;
    protected float minimum;
    protected float interval;
    protected String unit = "";
    /**
     * 辅助收敛坐标轴
     */
    protected float narrowMax;
    protected float narrowMin;
    /**
     * 小数位数
     */
    protected int decimalPlaces;
    /**
     * 数据坐标与实际坐标比例
     */
    protected float axisScale;

    public void setAxisLength(float axisLength) {
        this.axisLength = axisLength;
    }

    public float getAxisLength() {
        return axisLength;
    }

    public void setMaximum(float maximum) {
        this.maximum = maximum;
    }

    public float getMaximum() {
        return maximum;
    }

    public void setMinimum(float minimum) {
        this.minimum = minimum;
    }

    public float getMinimum() {
        return minimum;
    }

    public void setInterval(float interval) {
        this.interval = interval;
    }

    public float getInterval() {
        return interval;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setDecimalPlaces(int decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public int getDecimalPlaces() {
        return decimalPlaces;
    }

    public void setAxisScale(float axisScale) {
        this.axisScale = axisScale;
    }

    public float getAxisScale() {
        return axisScale;
    }

    public void setNarrowMax(float narrowMax) {
        this.narrowMax = narrowMax;
    }

    public float getNarrowMax() {
        return narrowMax;
    }

    public void setNarrowMin(float narrowMin) {
        this.narrowMin = narrowMin;
    }

    public float getNarrowMin() {
        return narrowMin;
    }

    @Override
    public String toString() {
        return "AxisData{" +
                "axisLength=" + axisLength +
                ", maximum=" + maximum +
                ", minimum=" + minimum +
                ", interval=" + interval +
                ", unit='" + unit + '\'' +
                ", narrowMax=" + narrowMax +
                ", narrowMin=" + narrowMin +
                ", decimalPlaces=" + decimalPlaces +
                ", axisScale=" + axisScale +
                '}';
    }
}
