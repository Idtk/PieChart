package com.idtk.smallchart.chart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

import com.idtk.smallchart.animation.ChartAnimator;
import com.idtk.smallchart.data.LineData;
import com.idtk.smallchart.data.PointData;
import com.idtk.smallchart.interfaces.IChart.ILineChart;
import com.idtk.smallchart.render.LineChartRender;

/**
 * Created by Idtk on 2016/6/6.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 */
public class LineChart extends BarLineCurveChart<LineData> implements ILineChart{

    private LineChartRender mLineChartRender;
    private ChartAnimator mChartAnimator;
    private float animatedValue;
    private PointData mPointData = new PointData();
    private float pointOutRadius;
    private float pointInRadius;

    public LineChart(Context context) {
        super(context);
    }

    public LineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Test
     */
    private Path mPath = new Path();
    private Paint linePaint = new Paint();

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        pointOutRadius = mXAxisData.getAxisLength()/70;
        pointInRadius = mXAxisData.getAxisLength()/100;
        mPointData.setInRadius(pointInRadius);
        mPointData.setOutRadius(pointOutRadius);
        mLineChartRender = new LineChartRender(mXAxisData,mYAxisData,mPointData);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i=0; i<mDataList.size(); i++){
            canvas.save();
            canvas.scale(1,-1);
//            mLineChartRender=new LineChartRender(mDataList.get(i),animatedValue,mXAxisData,mYAxisData);
            mLineChartRender.drawGraph(canvas,mDataList.get(i),animatedValue);
            canvas.restore();
        }
    }

    @Override
    protected void init() {

    }

    @Override
    protected void Animated() {
        mAnimatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatedValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        };
        mChartAnimator = new ChartAnimator(mAnimatorUpdateListener);
        mChartAnimator.animatedY(4000,mYAxisData.getAxisLength());
    }

    public void setPointInRadius(float pointInRadius) {
        this.pointInRadius = pointInRadius;
    }

    public void setPointOutRadius(float pointOutRadius) {
        this.pointOutRadius = pointOutRadius;
    }
}
