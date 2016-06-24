package com.idtk.smallchart.interfaces.iData;

import android.graphics.RectF;

/**
 * Created by Idtk on 2016/6/22.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 扇形图坐标系数据接口
 */
public interface IPieAxisData extends IChartData {

    /**
     * 获取透明扇形内半径与扇形半径比
     * @return 比例
     */
    float getInsideRadiusScale();

    /**
     * 设置透明扇形内半径与扇形半径比
     * @param insideRadiusScale 比例
     */
    void setInsideRadiusScale(float insideRadiusScale);

    /**
     * 获取透明扇形外半径与扇形半径比
     * @return 比例
     */
    float getOutsideRadiusScale();

    /**
     * 设置透明扇形外半径与扇形半径比
     * @param outsideRadiusScale 比例
     */
    void setOutsideRadiusScale(float outsideRadiusScale);

    /**
     * 获取点击后偏移扇形的半径与扇形半径比
     * @return 比例
     */
    float getOffsetRadiusScale();

    /**
     * 设置点击后偏移扇形的半径与扇形半径比
     * @param offsetRadiusScale 比例
     */
    void setOffsetRadiusScale(float offsetRadiusScale);

    /**
     * 获取扇形图的起始角度
     * @return 角度
     */
    float getStartAngle();

    /**
     * 设置扇形图的起始角度
     * @param startAngle 角度
     */
    void setStartAngle(float startAngle);

    /**
     * 获取扇形图显示单个扇形区域百分比时，大于的最小角度
     * @return 角度
     */
    float getMinAngle();

    /**
     * 设置扇形图显示单个扇形区域百分比文本时，必须大于的最小角度
     * @param minAngle 角度
     */
    void setMinAngle(float minAngle);

    /**
     * 获取显示百分比数值的需要设置小数位数
     * @return 小数位数
     */
    int getDecimalPlaces();

    /**
     * 设置显示百分比数值的需要设置小数位数
     * @param decimalPlaces 小数位数
     */
    void setDecimalPlaces(int decimalPlaces);

    /**
     * 扇形区域的起始角度集合
     * @return 集合
     */
    float[] getStartAngles();

    /**
     * 扇形区域的起始角度集合
     * @param startAngles 集合
     */
    void setStartAngles(float[] startAngles);

    /**
     * 获取正常的扇形图的矩形、透明扇形图内外矩形
     * @return 矩形集合
     */
    RectF[] getRectFs();

    /**
     * 设置正常的扇形图的矩形、透明扇形图内外矩形
     * @param rectFs 矩形集合
     */
    void setRectFs(RectF[] rectFs);

    /**
     * 获取偏移的扇形图的矩形、透明扇形图内外矩形
     * @return 矩形集合
     */
    RectF[] getOffsetRectFs();

    /**
     * 设置偏移的扇形图的矩形、透明扇形图内外矩形
     * @param offsetRectFs 矩形集合
     */
    void setOffsetRectFs(RectF[] offsetRectFs);

    /**
     * 获取扇形图半径
     * @return 半径
     */
    float getAxisLength();

    /**
     * 设置扇形图半径
     * @param axisLength 半径
     */
    void setAxisLength(float axisLength);
}
