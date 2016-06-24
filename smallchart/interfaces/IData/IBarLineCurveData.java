package com.idtk.smallchart.interfaces.iData;

import android.graphics.PointF;

import java.util.ArrayList;

/**
 * Created by Idtk on 2016/6/6.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 柱状图、折线图、曲线图数据类接口
 */
public interface IBarLineCurveData extends IChartData {

    /**
     * 设置图表数据
     * @param value 图表数据
     */
    void setValue(ArrayList<PointF> value);

    /**
     * 获取图表数据
     * @return 图表数据
     */
    ArrayList<PointF> getValue();
}
