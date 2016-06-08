package com.idtk.smallchart.interfaces.IChart;

/**
 * Created by Administrator on 2016/6/7.
 */
public interface IBarLineCurveChart extends IChart{
    void computeAxis();

    void setAxisTextSize(int axisTextSize);

    void setAxisColor(int axisColor);

    void setAxisWidth(int axisWidth);

    void setXAxisUnit(String XAxisUnit);

    void setYAxisUnit(String YAxisUnit);

    void setConvergenceFlag(boolean convergenceFlag);
}
