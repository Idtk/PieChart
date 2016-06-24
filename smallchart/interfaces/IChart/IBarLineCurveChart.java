package com.idtk.smallchart.interfaces.iChart;

/**
 * Created by Idtk on 2016/6/7.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 柱状图、折线图、曲线图绘制接口
 */
public interface IBarLineCurveChart{
    /**
     * 计算X轴最大值、最小值、区间长度
     */
    void computeXAxis();

    /**
     * 计算Y轴最大值、最小值、区间长度
     */
    void computeYAxis();

    /**
     * 坐标轴字符大小设置
     * @param axisTextSize 字符大小
     *//*
    void setAxisTextSize(float axisTextSize);

    *//**
     * 坐标轴颜色设置
     * @param axisColor 颜色
     *//*
    void setAxisColor(int axisColor);

    *//**
     * 坐标轴宽度设置
     * @param axisWidth 宽度
     *//*
    void setAxisWidth(float axisWidth);*/

    /**
     * Y坐标轴单位设置
     * @param XAxisUnit 单位字符串
     */
    void setXAxisUnit(String XAxisUnit);

    /**
     * X坐标轴单位设置
     * @param YAxisUnit 单位字符串
     */
    void setYAxisUnit(String YAxisUnit);
}
