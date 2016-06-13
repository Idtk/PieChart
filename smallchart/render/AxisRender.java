package com.idtk.smallchart.render;

import android.graphics.Canvas;

/**
 * Created by Idtk on 2016/6/12.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 */
public abstract class AxisRender extends Render {
    public AxisRender() {
        super();
    }

    public abstract void drawGraph(Canvas canvas);
}
