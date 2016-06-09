package com.idtk.smallchart.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.idtk.smallchart.compute.ComputeXAxis;
import com.idtk.smallchart.compute.ComputeYAxis;
import com.idtk.smallchart.data.XAxisData;
import com.idtk.smallchart.data.YAxisData;
import com.idtk.smallchart.interfaces.IChart.IBarLineCurveChart;
import com.idtk.smallchart.interfaces.IData.IBarLineCurveData;
import com.idtk.smallchart.render.TouchRender;
import com.idtk.smallchart.render.XAxisRender;
import com.idtk.smallchart.render.YAxisRender;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/6.
 */
public abstract class BarLineCurveChart<T extends IBarLineCurveData> extends Chart<T> implements IBarLineCurveChart {

    protected XAxisData mXAxisData = new XAxisData();
    protected YAxisData mYAxisData = new YAxisData();
    protected ComputeXAxis mComputeXAxis = new ComputeXAxis(mXAxisData);
    protected ComputeYAxis mComputeYAxis = new ComputeYAxis(mYAxisData);

    protected ArrayList<T> mDataList = new ArrayList<>();
//    protected NumberFormat numberFormat;
    protected boolean convergenceFlag = true;

    protected XAxisRender mXAxisRender;
    protected YAxisRender mYAxisRender;

    protected int axisTextSize = 30;
    protected int axisColor = Color.BLACK;
    protected int axisWidth = 1;
    protected String XAxisUnit = "";
    protected String YAxisUnit = "";

    protected boolean touchFlag = true;
    protected int touchId = -1,touchArrayId=-1;

    private PointF touchPoint = new PointF();
    private TouchRender mTouchRender = new TouchRender();

    public BarLineCurveChart(Context context) {
        super(context);
    }

    public BarLineCurveChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BarLineCurveChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mXAxisData.setAxisLength(mWidth*0.8f);
        mYAxisData.setAxisLength(mHeight*0.8f);
        mXAxisData.setTextSize(axisTextSize);
        mYAxisData.setTextSize(axisTextSize);
        mXAxisData.setColor(axisColor);
        mYAxisData.setColor(axisColor);
        mXAxisData.setPaintWidth(axisWidth);
        mYAxisData.setPaintWidth(axisWidth);

        Animated();

        if (convergenceFlag){

            /**
             * 循环收敛
             */
            mComputeXAxis.convergence(mDataList);
            mComputeYAxis.convergence(mDataList);
        }

        mXAxisData.setAxisScale(mXAxisData.getAxisLength()/(mXAxisData.getMaximum()-mXAxisData.getMinimum()));
        mYAxisData.setAxisScale(mYAxisData.getAxisLength()/(mYAxisData.getMaximum()-mYAxisData.getMinimum()));
        mXAxisRender = new XAxisRender(mXAxisData);
        mYAxisRender = new YAxisRender(mYAxisData,mXAxisData);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.translate(mViewWidth/2-mXAxisData.getAxisLength()/2,mViewHeight/2+mYAxisData.getAxisLength()/2);
        canvas.save();
        canvas.scale(1,-1);
        mXAxisRender.drawGraph(canvas);
        mYAxisRender.drawGraph(canvas);
        canvas.restore();

        drawGraphical(canvas);

        NumberFormat numberFormatX = NumberFormat.getNumberInstance();
        numberFormatX.setMaximumFractionDigits(mXAxisData.getDecimalPlaces());

        NumberFormat numberFormatY = NumberFormat.getNumberInstance();
        numberFormatY.setMaximumFractionDigits(mYAxisData.getDecimalPlaces());

        for (int i=0; i<mDataList.size(); i++){
            for (int j=0; j<mDataList.get(i).getValue().size(); j++){
                if (i==touchArrayId&&j==touchId){
                    touchPoint.x = (mDataList.get(touchArrayId).getValue().get(touchId).x-mXAxisData.getMinimum())*mXAxisData.getAxisScale();
                    //倒置Y轴坐标
                    touchPoint.y = -(mDataList.get(touchArrayId).getValue().get(touchId).y-mYAxisData.getMinimum())*mYAxisData.getAxisScale();
                    mTouchRender.drawPointAxis(canvas,numberFormatX.format(mDataList.get(touchArrayId).getValue().get(touchId).x),
                            numberFormatY.format(mDataList.get(touchArrayId).getValue().get(touchId).y),touchPoint);
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
    public void computeAxis() {
        mComputeXAxis.computeXAxis(mDataList);
        mComputeYAxis.computeYAxis(mDataList);
//        LogUtil.d("TAG",mXAxisData.toString());
//        LogUtil.d("TAG",mYAxisData.toString());
    }

    @Override
    public void setData(T chartData) {
        mDataList.clear();
        mDataList.add(chartData);
        computeAxis();
    }

    public void setDataList(ArrayList<T> chartDataList) {
        mDataList = chartDataList;
        computeAxis();
    }

    protected abstract void Animated();

    protected abstract void drawGraphical(Canvas canvas);

    @Override
    public void setAxisTextSize(int axisTextSize) {
        this.axisTextSize = axisTextSize;
    }

    @Override
    public void setAxisColor(int axisColor) {
        this.axisColor = axisColor;
    }

    @Override
    public void setAxisWidth(int axisWidth) {
        this.axisWidth = axisWidth;
    }

    @Override
    public void setXAxisUnit(String XAxisUnit) {
        this.XAxisUnit = XAxisUnit;
    }

    @Override
    public void setYAxisUnit(String YAxisUnit) {
        this.YAxisUnit = YAxisUnit;
    }

    public void setConvergenceFlag(boolean convergenceFlag) {
        this.convergenceFlag = convergenceFlag;
    }

    public void setTouchFlag(boolean touchFlag) {
        this.touchFlag = touchFlag;
    }
}
