package com.idtk.smallchart.interfaces.iChart;

/**
 * Created by Idtk on 2016/6/20.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 雷达图绘制类接口
 */
public interface IRadarChart{
    /**
     * 计算雷达图坐标
     */
    void computeRadar();

    /**
     * 设置各角类型文本颜色
     * @param color 颜色
     */
    void setAxisValueColor(int color);

    /**
     *设置各角文本集合
     * @param types 文本字符串集合
     */
    void setTypes(String[] types);
}
