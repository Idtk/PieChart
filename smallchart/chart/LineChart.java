package com.idtk.smallchart.chart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.idtk.smallchart.animation.ChartAnimator;
import com.idtk.smallchart.data.LineData;
import com.idtk.smallchart.data.PointData;
import com.idtk.smallchart.interfaces.IChart.ILineChart;
import com.idtk.smallchart.render.LineChartRender;
import com.idtk.smallchart.render.XAxisRender;
import com.idtk.smallchart.render.YAxisRender;

/**
 * Created by Idtk on 2016/6/6.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 */
public class LineChart extends BarLineCurveChart<LineData> implements ILineChart{

    private LineChartRender mLineChartRender;
    private PointData mPointData = new PointData();
    private float pointOutRadius;
    private float pointInRadius;
    private PointData.PointShape pointShape  = PointData.PointShape.CIRCLE;
    private boolean isPointInRadius = false, isPointOutRadius=false;

    public LineChart(Context context) {
        super(context);
    }

    public LineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (!isPointOutRadius){
            pointOutRadius = mXAxisData.getAxisLength()/70;
        }
        if (!isPointInRadius){
            pointInRadius = mXAxisData.getAxisLength()/100;
        }
        mPointData.setInRadius(pointInRadius);
        mPointData.setOutRadius(pointOutRadius);
        mPointData.setPointShape(pointShape);
        chartRenderList.clear();
        for (int i=0; i<mDataList.size(); i++){
            mLineChartRender = new LineChartRender(mDataList.get(i),mXAxisData,mYAxisData,mPointData,textSize);
            chartRenderList.add(mLineChartRender);
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

    @Override
    protected void drawGraphical(Canvas canvas) {
        /*for (int i=0; i<mDataList.size(); i++){
            canvas.save();
            canvas.scale(1,-1);
            mLineChartRender.drawGraph(canvas,mDataList.get(i),animatedValue);
            canvas.restore();
        }*/
        for (int i=0; i<chartRenderList.size(); i++){
            canvas.save();
            canvas.scale(1,-1);
            chartRenderList.get(i).drawGraph(canvas,animatedValue);
            canvas.restore();
        }
    }

    @Override
    protected void init() {

    }

    @Override
    protected void animated() {
        animatedTarget = mYAxisData.getAxisLength();
        if (!isAnimated) {
            animatedValue = mYAxisData.getAxisLength();
        } else {
            mAnimatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    animatedValue = (float) animation.getAnimatedValue();
                    invalidate();
                }
            };
            mChartAnimator = new ChartAnimator(mAnimatorUpdateListener);
            mChartAnimator.animatedY(2000,mYAxisData.getAxisLength());
        }
    }

    public void setPointInRadius(float pointInRadius) {
        isPointInRadius = true;
        this.pointInRadius = pointInRadius;
    }

    public void setPointOutRadius(float pointOutRadius) {
        isPointOutRadius = true;
        this.pointOutRadius = pointOutRadius;
    }

    public void setPointShape(PointData.PointShape pointShape) {
        this.pointShape = pointShape;
    }
}
