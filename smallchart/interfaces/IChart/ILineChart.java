package com.idtk.smallchart.interfaces.IChart;

import com.idtk.smallchart.data.PointData;

/**
 * Created by Administrator on 2016/6/7.
 */
public interface ILineChart extends IBarLineCurveChart {

    void setPointInRadius(float pointInRadius);
    void setPointOutRadius(float pointOutRadius);
    void setPointShape(PointData.PointShape pointShape);
}
