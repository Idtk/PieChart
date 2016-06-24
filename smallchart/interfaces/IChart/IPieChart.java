package com.idtk.smallchart.interfaces.iChart;

/**
 * Created by Idtk on 2016/6/22.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 扇形图绘制接口
 */
public interface IPieChart {

    /**
     * 计算扇形图数据
     */
    void computePie();
    /**
     * 设置扇形图 透明扇形内半径与扇形半径比
     * @param insideRadiusScale 比例
     */
    void setInsideRadiusScale(float insideRadiusScale);

    /**
     * 设置透明扇形外半径与扇形半径比
     * @param outsideRadiusScale 比例
     */
    void setOutsideRadiusScale(float outsideRadiusScale);

    /**
     * 设置点击后偏移扇形的半径与扇形半径比
     * @param offsetRadiusScale 比例
     */
    void setOffsetRadiusScale(float offsetRadiusScale);

    /**
     * 设置扇形图的起始角度
     * @param startAngle 角度
     */
    void setStartAngle(float startAngle);

    /**
     * 设置扇形图显示单个扇形区域百分比文本时，必须大于的最小角度
     * @param minAngle 角度
     */
    void setMinAngle(float minAngle);

    /**
     * 设置显示百分比数值的需要设置小数位数
     * @param decimalPlaces 小数位数
     */
    void setDecimalPlaces(int decimalPlaces);
}
