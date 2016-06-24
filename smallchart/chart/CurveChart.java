package com.idtk.smallchart.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.idtk.smallchart.interfaces.iChart.ICurveChart;
import com.idtk.smallchart.interfaces.iData.ICurveData;
import com.idtk.smallchart.render.CurveChartRender;
import com.idtk.smallchart.render.XAxisRender;
import com.idtk.smallchart.render.YAxisRender;

/**
 * Created by Idtk on 2016/6/6.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 曲线图绘制类
 */
public class CurveChart extends BarLineCurveChart<ICurveData> implements ICurveChart{

    private CurveChartRender curveChartRender;
//    private PointData mPointData = new PointData();
    private float pointOutRadius;
    private float pointInRadius;
//    private PointData.PointShape pointShape  = PointData.PointShape.CIRCLE;
    private boolean isPointInRadius = false, isPointOutRadius=false;

    public CurveChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CurveChart(Context context) {
        super(context);
    }

    public CurveChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        /*if (!isPointOutRadius){
            pointOutRadius = mXAxisData.getAxisLength()/70;
        }
        if (!isPointInRadius){
            pointInRadius = mXAxisData.getAxisLength()/100;
        }

        mPointData.setInRadius(pointInRadius);
        mPointData.setOutRadius(pointOutRadius);
        mPointData.setPointShape(pointShape);*/
        chartRenderList.clear();
        for (int i=0; i<mDataList.size(); i++){
            curveChartRender = new CurveChartRender(mDataList.get(i),mXAxisData,mYAxisData,0);
            chartRenderList.add(curveChartRender);
        }
    }

    @Override
    protected void axisScale() {
        mXAxisData.setAxisScale(mXAxisData.getAxisLength()/(mXAxisData.getMaximum()-mXAxisData.getMinimum()));
        mYAxisData.setAxisScale(mYAxisData.getAxisLength()/(mYAxisData.getMaximum()-mYAxisData.getMinimum()));
        mXAxisRender = new XAxisRender(mXAxisData);
        mYAxisRender = new YAxisRender(mYAxisData,mXAxisData);
    }
    @Override
    protected void axisRender(Canvas canvas) {
        mXAxisRender.drawGraph(canvas);
        mYAxisRender.drawGraph(canvas);
    }

}
