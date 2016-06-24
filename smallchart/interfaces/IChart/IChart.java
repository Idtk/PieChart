package com.idtk.smallchart.interfaces.iChart;

/**
 * Created by Idtk on 2016/6/7.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 所有绘制的基础接口
 */
public interface IChart {

    /**
     * 坐标轴字符大小设置
     * @param axisTextSize 字符大小
     */
    void setAxisTextSize(float axisTextSize);

    /**
     * 坐标轴颜色设置
     * @param axisColor 颜色
     */
    void setAxisColor(int axisColor);

    /**
     * 坐标轴宽度设置
     * @param axisWidth 宽度
     */
    void setAxisWidth(float axisWidth);

}
