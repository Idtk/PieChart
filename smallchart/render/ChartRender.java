package com.idtk.smallchart.render;

import android.graphics.Canvas;

/**
 * Created by Idtk on 2016/6/8.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 ; 图表渲染基类
 */
public abstract class ChartRender extends Render{

    public ChartRender() {
        super();
    }

    /**
     * 图表渲染方法
     * @param canvas 画布
     * @param animatedValue 交互动画值
     */
    public abstract void drawGraph(Canvas canvas,float animatedValue);

}
