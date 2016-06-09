package com.idtk.smallchart.render;

import android.graphics.Canvas;

import com.idtk.smallchart.data.CurveData;

/**
 * Created by Idtk on 2016/6/6.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 */
public class CurveChartRender extends BarLineCurveRender<CurveData> {
    public CurveChartRender() {
        super();
    }

    @Override
    public void drawGraph(Canvas canvas) {
//        canvas.drawText("CurveChartRender",0,0,paint);
    }

    @Override
    public void drawGraph(Canvas canvas, CurveData iBarLineCurveData, float animatedValue) {



    }
}
