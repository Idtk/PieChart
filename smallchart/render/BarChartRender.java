package com.idtk.smallchart.render;


import android.graphics.Canvas;

import com.idtk.smallchart.data.BarData;

/**
 * Created by Idtk on 2016/6/6.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 */
public class BarChartRender extends BarLineCurveRender<BarData> {

    public BarChartRender() {
        super();
    }

    @Override
    public void drawGraph(Canvas canvas) {
//        canvas.drawText("BarChartRender",0,0,paint);
    }

    @Override
    public void drawGraph(Canvas canvas, BarData iBarLineCurveData,float animatedValue) {

    }
}
