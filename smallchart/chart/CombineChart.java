package com.idtk.smallchart.chart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.idtk.smallchart.animation.ChartAnimator;
import com.idtk.smallchart.data.BarData;
import com.idtk.smallchart.data.CurveData;
import com.idtk.smallchart.data.LineData;
import com.idtk.smallchart.data.PointData;
import com.idtk.smallchart.interfaces.IChart.IBarChart;
import com.idtk.smallchart.interfaces.IChart.ICurveChart;
import com.idtk.smallchart.interfaces.IChart.ILineChart;
import com.idtk.smallchart.interfaces.IData.IBarLineCurveData;
import com.idtk.smallchart.render.BarChartRender;
import com.idtk.smallchart.render.CurveChartRender;
import com.idtk.smallchart.render.LineChartRender;
import com.idtk.smallchart.render.XAxisOffsetRender;
import com.idtk.smallchart.render.YAxisRender;

/**
 * Created by Idtk on 2016/6/6.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 */
public class CombineChart extends BarLineCurveChart<IBarLineCurveData> implements IBarChart,ILineChart,ICurveChart{

    private BarChartRender mBarChartRender;
    private LineChartRender mLineChartRender;
    private CurveChartRender curveChartRender;
    private float barWidth = 30;
    private float intensity = 0.2f;
    private PointData mPointData = new PointData();
    private float pointOutRadius;
    private float pointInRadius;
    private PointData.PointShape pointShape  = PointData.PointShape.CIRCLE;
    private boolean isPointInRadius = false, isPointOutRadius=false;

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
        float offset ;
        int j=0,k=0;

        for (int i=0; i<mDataList.size(); i++){
            String cl = mDataList.get(i).getClass().getSimpleName().toString();
            if (cl.equals("BarData")){
                k++;
            }
        }

        for (int i=0; i<mDataList.size(); i++){
            String cl = mDataList.get(i).getClass().getSimpleName().toString();
            if (cl.equals("BarData")){
//                LogUtil.d("TAG2",cl);
                offset = mXAxisData.getInterval()*mXAxisData.getAxisScale()/2-barWidth*k/2+barWidth*j;
                mBarChartRender = new BarChartRender((BarData) mDataList.get(i),mXAxisData,mYAxisData,offset,barWidth,textSize);
                chartRenderList.add(mBarChartRender);
                j++;
            }else if (cl.equals("CurveData")){
                curveChartRender = new CurveChartRender((CurveData) mDataList.get(i),mXAxisData,mYAxisData,
                        mPointData,textSize,mXAxisData.getInterval()*mXAxisData.getAxisScale()/2);
                chartRenderList.add(curveChartRender);
            }else if (cl.equals("LineData")){
                mLineChartRender = new LineChartRender((LineData) mDataList.get(i),mXAxisData,mYAxisData,
                        mPointData,textSize,mXAxisData.getInterval()*mXAxisData.getAxisScale()/2);
                chartRenderList.add(mLineChartRender);
            }
        }
        j=0;
        k=0;
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

    public void setIntensity(float intensity) {
        this.intensity = intensity;
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
