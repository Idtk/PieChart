package com.idtk.smallchart.interfaces.iData;

/**
 * Created by Idtk on 2016/6/22.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 扇形图数据接口
 */
public interface IPieData extends IChartData {
    /**
     * 设置单个扇形区域的数据
     * @param value 数据
     */
    void setValue(float value);

    /**
     * 获取单个扇形区域的数据
     * @return 数据
     */
    float getValue();

    /**
     * 设置扇形区域的百分比
     * @param percentage 百分比小数
     */
    void setPercentage(float percentage);

    /**
     * 获取扇形区域百分比
     * @return 百分比小数
     */
    float getPercentage();

    /**
     * 设置扇形区域经过角度
     * @param angle 角度
     */
    void setAngle(float angle);

    /**
     * 获取扇形区域经过角度
     * @return 角度
     */
    float getAngle();

    /**
     * 获取扇形区域的当前起始角度
     * @return 角度
     */
    float getCurrentAngle();

    /**
     * 设置扇形区域的当前起始角度
     * @param currentAngle 角度
     */
    void setCurrentAngle(float currentAngle);

    /**
     * 获取百分比文字颜色
     * @return 颜色
     */
//    int getTextColor();

    /**
     * 设置百分比文字颜色
     * @param textColor 颜色
     */
//    void setTextColor(int textColor);
}
