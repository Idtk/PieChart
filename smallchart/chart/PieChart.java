package com.idtk.smallchart.chart;

import android.content.Context;
import android.graphics.Canvas;

import com.idtk.smallchart.interfaces.IChart.IPieChart;
import com.idtk.smallchart.interfaces.IData.IPieData;

/**
 * Created by Idtk on 2016/6/22.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 扇形图绘制类
 * isAnimated、isTouch
 * 子Pie集合操作 : widths、textSizes、
 */
public class PieChart extends PieRadarChart<IPieData> implements IPieChart{
    public PieChart(Context context) {
        super(context);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
