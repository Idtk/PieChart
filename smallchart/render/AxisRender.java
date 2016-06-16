package com.idtk.smallchart.render;

import android.graphics.Canvas;

/**
 * Created by Idtk on 2016/6/12.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 ; 坐标轴渲染基类
 */
public abstract class AxisRender extends Render {
    public AxisRender() {
        super();
    }

    /**
     * 坐标轴渲染方法
     * @param canvas 画布
     */
    public abstract void drawGraph(Canvas canvas);
}
