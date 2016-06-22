package com.idtk.smallchart.interfaces.IChart;

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
     * 设置各角类型文本粗细
     * @param webWidth 宽度
     */
    void setWebWidth(float webWidth);

    /**
     *设置各角文本集合
     * @param types 文本字符串集合
     */
    void setTypes(String[] types);

    /**
     * 设置文本字符大小
     * @param textSize 字符大小
     */
    void setWebTextSize(float textSize);
}
