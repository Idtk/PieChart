package com.idtk.smallchart.chart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.idtk.smallchart.animation.ChartAnimator;
import com.idtk.smallchart.data.LineData;
import com.idtk.smallchart.data.PointData;
import com.idtk.smallchart.interfaces.IChart.ILineChart;
import com.idtk.smallchart.render.LineChartRender;
import com.idtk.smallchart.render.TouchRender;

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
    private boolean touchFlag = true;
    private int touchId = -1,touchArrayId=-1;
    private PointF touchPoint = new PointF();
    private TouchRender mTouchRender = new TouchRender();

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
        for (int i=0; i<mDataList.size(); i++){
            for (int j=0; j<mDataList.get(i).getValue().size(); j++){
                if (i==touchArrayId&&j==touchId){
                    touchPoint.x = (mDataList.get(touchArrayId).getValue().get(touchId).x-mXAxisData.getMinimum())*mXAxisData.getAxisScale();
                    //倒置Y轴坐标
                    touchPoint.y = -(mDataList.get(touchArrayId).getValue().get(touchId).y-mYAxisData.getMinimum())*mYAxisData.getAxisScale();
                    mTouchRender.drawPointAxis(canvas,mDataList.get(touchArrayId).getValue().get(touchId).x,
                            mDataList.get(touchArrayId).getValue().get(touchId).y,touchPoint);
//                    LogUtil.d("TAG",touchPoint.x+":"+touchPoint.y+":"+mXAxisData.getAxisLength()+":"+mYAxisData.getAxisLength());
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (touchFlag){
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    float x = event.getX()-(mViewWidth/2-mXAxisData.getAxisLength()/2);
                    float y = (mViewHeight/2+mYAxisData.getAxisLength()/2)-event.getY();
                    drawTouch(x,y);
                    invalidate();
                    return true;
                case MotionEvent.ACTION_UP:
                    touchId = -1;
                    touchArrayId = -1;
                    invalidate();
                    return true;
            }
        }
        return super.onTouchEvent(event);
    }


    private void drawTouch(float x, float y){
        int minI =-1;
        int minJ =-1;
        for (int i=0; i< mDataList.size(); i++){
            for (int j=0; j< mDataList.get(i).getValue().size(); j++){
                if (Math.abs((mDataList.get(i).getValue().get(j).x-mXAxisData.getMinimum())*mXAxisData.getAxisScale()-x)<(mXAxisData.getAxisLength()/50)||
                        (Math.abs(mDataList.get(i).getValue().get(j).y-mYAxisData.getMinimum())*mYAxisData.getAxisScale()-y)<(mXAxisData.getAxisLength()/50)){
                    float gapX = (mDataList.get(i).getValue().get(j).x-mXAxisData.getMinimum())*mXAxisData.getAxisScale()-x;
                    float gapY = (mDataList.get(i).getValue().get(j).y-mYAxisData.getMinimum())*mYAxisData.getAxisScale()-y;
                    double radius = Math.hypot(gapX,gapY);
                    if (radius<mXAxisData.getAxisLength()/50){
                        minI = i;
                        minJ = j;
                    }
                }
            }
        }
        touchArrayId = minI;
        touchId = minJ ;
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

    public void setTouchFlag(boolean touchFlag) {
        this.touchFlag = touchFlag;
    }
}
