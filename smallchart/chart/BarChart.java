package com.idtk.smallchart.chart;


import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.idtk.smallchart.animation.ChartAnimator;
import com.idtk.smallchart.data.BarData;
import com.idtk.smallchart.interfaces.IChart.IBarChart;
import com.idtk.smallchart.render.BarChartRender;
import com.idtk.smallchart.render.XAxisOffsetRender;
import com.idtk.smallchart.render.YAxisRender;

/**
 * Created by Idtk on 2016/6/6.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 */
public class BarChart extends BarLineCurveChart<BarData> implements IBarChart{

    private BarChartRender mBarChartRender;
    private float barWidth = 30;

    public BarChart(Context context) {
        super(context);
    }

    public BarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BarChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        chartRenderList.clear();
        float offset ;
        for (int i=0; i<mDataList.size(); i++){
            offset = mXAxisData.getInterval()*mXAxisData.getAxisScale()/2-barWidth*mDataList.size()/2+barWidth*i;
            mBarChartRender = new BarChartRender(mDataList.get(i),mXAxisData,mYAxisData,offset,barWidth,textSize);
            chartRenderList.add(mBarChartRender);
        }
    }

    @Override
    protected void init() {

    }

    @Override
    protected void animated() {
        if (!isAnimated){
            animatedValue = 1;
        }
        else {
            mAnimatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    animatedValue = (float) animation.getAnimatedValue();
                    invalidate();
                }
            };
            mChartAnimator = new ChartAnimator(mAnimatorUpdateListener);
            mChartAnimator.animatedY(2000,1);
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

    @Override
    protected void drawGraphical(Canvas canvas) {
        for (int i=0; i<chartRenderList.size(); i++){
//            canvas.save();
//            canvas.scale(1,-1);
            chartRenderList.get(i).drawGraph(canvas,animatedValue);
//            canvas.restore();
        }
    }

    public void setBarWidth(float barWidth) {
        this.barWidth = barWidth;
    }

    @Override
    public void computeYAxis() {
        mComputeYAxis.computeYAxisMin(mDataList);
    }
}
