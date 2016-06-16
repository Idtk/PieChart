package com.idtk.smallchart.interfaces.IChart;

import com.idtk.smallchart.data.PointData;

/**
 * Created by Idtk on 2016/6/7.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 曲线图绘制接口
 */
public interface ICurveChart extends IBarLineCurveChart {

    /**
     * 设置图表点内半径
     * @param pointInRadius 内半径
     */
    void setPointInRadius(float pointInRadius);

    /**
     * 设置图表点外半径
     * @param pointOutRadius 外半径
     */
    void setPointOutRadius(float pointOutRadius);

    /**
     * 设置图表点形状
     * @param pointShape 形状
     */
    void setPointShape(PointData.PointShape pointShape);
}
