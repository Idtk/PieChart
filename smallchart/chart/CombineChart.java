package com.idtk.smallchart.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.idtk.smallchart.interfaces.iChart.IBarChart;
import com.idtk.smallchart.interfaces.iChart.ICurveChart;
import com.idtk.smallchart.interfaces.iChart.ILineChart;
import com.idtk.smallchart.interfaces.iData.IBarData;
import com.idtk.smallchart.interfaces.iData.IBarLineCurveData;
import com.idtk.smallchart.interfaces.iData.ICurveData;
import com.idtk.smallchart.interfaces.iData.ILineData;
import com.idtk.smallchart.render.BarChartRender;
import com.idtk.smallchart.render.CurveChartRender;
import com.idtk.smallchart.render.LineChartRender;
import com.idtk.smallchart.render.XAxisOffsetRender;
import com.idtk.smallchart.render.YAxisRender;

/**
 * Created by Idtk on 2016/6/6.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 混合图表绘制类
 */
public class CombineChart extends BarLineCurveChart<IBarLineCurveData> implements IBarChart,ILineChart,ICurveChart{

    private BarChartRender mBarChartRender;
    private LineChartRender mLineChartRender;
    private CurveChartRender curveChartRender;
    private float barWidth = 30;
//    private PointData mPointData = new PointData();
//    private float pointOutRadius;
//    private float pointInRadius;
//    private PointData.PointShape pointShape  = PointData.PointShape.CIRCLE;
//    private boolean isPointInRadius = false, isPointOutRadius=false;

    public CombineChart(Context context) {
        super(context);
    }

    public CombineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CombineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        /**
         * 如果用户没有输入点的外半径值，则设置默认值
         */
        /*if (!isPointOutRadius){
            pointOutRadius = mXAxisData.getAxisLength()/70;
        }
        *//**
         * 如果用户没有输入点的内半径值，则设置默认值
         *//*
        if (!isPointInRadius){
            pointInRadius = mXAxisData.getAxisLength()/100;
        }*/

//        mPointData.setInRadius(pointInRadius);
//        mPointData.setOutRadius(pointOutRadius);
//        mPointData.setPointShape(pointShape);

        /**
         * 装载渲染
         */
        chartRenderList.clear();
        float offset ;
        int j=0,k=0;

        /**
         * 计算柱状图个数
         */
        for (int i=0; i<mDataList.size(); i++){
            String cl = mDataList.get(i).getClass().getSimpleName();
            if (cl.equals("BarData")){
                k++;
            }
        }

        for (int i=0; i<mDataList.size(); i++){
            String cl = mDataList.get(i).getClass().getSimpleName();
            /**
             * 判断Data类型
             */
            switch (cl){
                case "BarData":
                    offset = mXAxisData.getInterval()*mXAxisData.getAxisScale()/2-barWidth*k/2+barWidth*j;
                    mBarChartRender = new BarChartRender((IBarData) mDataList.get(i),mXAxisData,mYAxisData,offset,barWidth);
                    chartRenderList.add(mBarChartRender);
                    j++;
                    break;
                case "CurveData":
                    curveChartRender = new CurveChartRender((ICurveData) mDataList.get(i),mXAxisData,mYAxisData,
                            mXAxisData.getInterval()*mXAxisData.getAxisScale()/2);
                    chartRenderList.add(curveChartRender);
                    break;
                case "LineData":
                    mLineChartRender = new LineChartRender((ILineData) mDataList.get(i),mXAxisData,mYAxisData,
                            mXAxisData.getInterval()*mXAxisData.getAxisScale()/2);
                    chartRenderList.add(mLineChartRender);
                    break;
            }
        }
    }

    @Override
    protected void axisScale() {
        mXAxisData.setAxisScale(mXAxisData.getAxisLength()/(mXAxisData.getMaximum()-mXAxisData.getMinimum()+mXAxisData.getInterval()));
        mYAxisData.setAxisScale(mYAxisData.getAxisLength()/(mYAxisData.getMaximum()-mYAxisData.getMinimum()));
        mXAxisOffsetRender = new XAxisOffsetRender(mXAxisData);
        mYAxisRender = new YAxisRender(mYAxisData,mXAxisData);
    }
    @Override
    protected void axisRender(Canvas canvas) {
        mXAxisOffsetRender.drawGraph(canvas);
        mYAxisRender.drawGraph(canvas);
    }

    public void setBarWidth(float barWidth) {
        this.barWidth = barWidth;
    }

    @Override
    public void computeYAxis() {
        mComputeYAxis.computeYAxisMin(mDataList);
    }
}
