package com.idtk.smallchart.interfaces.IData;

import android.graphics.PointF;

import java.util.ArrayList;

/**
 * Created by Idtk on 2016/6/6.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 */
public interface IBarLineCurveData extends IChartData {

    void setValue(ArrayList<PointF> value);

    ArrayList<PointF> getValue();

    void setPaintWidth(int paintWidth);

    int getPaintWidth();
}
