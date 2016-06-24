package com.idtk.smallchart.data;

import android.graphics.PointF;

import com.idtk.smallchart.interfaces.iData.IBarLineCurveData;

import java.util.ArrayList;

/**
 * Created by Idtk on 2016/6/6.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 ; 柱状图、折线图、曲线图数据基类
 */
public class BarLineCurveData extends ChartData implements IBarLineCurveData {
    protected ArrayList<PointF> value;

    public void setValue(ArrayList<PointF> value) {
        this.value = value;
    }

    public ArrayList<PointF> getValue() {
        return value;
    }

}
