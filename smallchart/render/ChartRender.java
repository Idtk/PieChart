package com.idtk.smallchart.render;

import android.graphics.Canvas;

/**
 * Created by Idtk on 2016/6/8.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 */
public abstract class ChartRender<T> extends Render{

    public abstract void drawGraph(Canvas canvas,float animatedValue);

}
