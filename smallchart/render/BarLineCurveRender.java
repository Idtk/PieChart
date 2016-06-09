package com.idtk.smallchart.render;

import android.graphics.Canvas;

/**
 * Created by Idtk on 2016/6/8.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 */
public abstract class BarLineCurveRender<T> extends Render{

    public abstract void drawGraph(Canvas canvas, T iBarLineCurveData, float animatedValue);

}
