package com.idtk.smallchart.interfaces.iData;

/**
 * Created by Idtk on 2016/6/6.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 坐标轴基类接口
 */
public interface IAxisData extends IBaseData{

    /**
     * 设置坐标轴长度
     * @param axisLength 长度
     */
    void setAxisLength(float axisLength);

    /**
     * 获取坐标轴长度
     * @return 长度
     */
    float getAxisLength();

    /**
     * 设置坐标轴最大值
     * @param maximum 最大值
     */
    void setMaximum(float maximum);

    /**
     * 获取坐标轴最大值
     * @return 最大值
     */
    float getMaximum();

    /**
     * 设置坐标轴最小值
     * @param minimum 最小值
     */
    void setMinimum(float minimum);

    /**
     * 获取坐标轴最小值
     * @return 最小值
     */
    float getMinimum();

    /**
     * 设置坐标轴区间值
     * @param interval 区间值
     */
    void setInterval(float interval);

    /**
     * 获取坐标轴区间值
     * @return 区间值
     */
    float getInterval();

    /**
     * 设置单位
     * @param unit 单位
     */
    void setUnit(String unit);

    /**
     * 获取单位
     * @return 单位
     */
    String getUnit();

    /**
     * 设置坐标轴小数点位数
     * @param decimalPlaces 小数点位数
     */
    void setDecimalPlaces(int decimalPlaces);

    /**
     * 获取坐标轴小数点位数
     * @return 小数点位数
     */
    int getDecimalPlaces();

    /**
     * 设置坐标轴刻度长度与View长度的比例
     * @param axisScale 比例
     */
    void setAxisScale(float axisScale);

    /**
     * 获取坐标轴刻度长度与View长度的比例
     * @return 比例
     */
    float getAxisScale();

    /**
     * 保存计算前的坐标轴最大值
     * @param narrowMax 最大值
     */
    void setNarrowMax(float narrowMax);

    /**
     * 获取计算前的坐标轴最大值
     * @return 最大值
     */
    float getNarrowMax();

    /**
     * 保存计算前的坐标轴最小值
     * @param narrowMin 最小值
     */
    void setNarrowMin(float narrowMin);

    /**
     * 获取计算前的坐标轴最小值
     * @return 最小值
     */
    float getNarrowMin();

}
