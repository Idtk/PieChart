package com.idtk.smallchart.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;

import com.idtk.smallchart.animation.ChartAnimator;
import com.idtk.smallchart.compute.ComputeXAxis;
import com.idtk.smallchart.compute.ComputeYAxis;
import com.idtk.smallchart.data.XAxisData;
import com.idtk.smallchart.data.YAxisData;
import com.idtk.smallchart.interfaces.IChart.IBarLineCurveChart;
import com.idtk.smallchart.interfaces.IData.IBarLineCurveData;
import com.idtk.smallchart.render.ChartRender;
import com.idtk.smallchart.render.XAxisOffsetRender;
import com.idtk.smallchart.render.XAxisRender;
import com.idtk.smallchart.render.YAxisRender;

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

    protected XAxisOffsetRender mXAxisOffsetRender;
    protected XAxisRender mXAxisRender;
    protected YAxisRender mYAxisRender;

    protected int axisTextSize = 30;
    protected int axisColor = Color.BLACK;
    protected int axisWidth = 2;
    protected String XAxisUnit = "";
    protected String YAxisUnit = "";

    protected int textSize = 30;

    protected ArrayList<ChartRender> chartRenderList = new ArrayList<>();
    protected float animatedValue;
    public boolean isAnimated = true;
    protected ChartAnimator mChartAnimator;
    protected float animatedTarget;

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

        animated();

        if (convergenceFlag){
            /**
             * 循环收敛
             */
            mComputeXAxis.convergence(mDataList);
            mComputeYAxis.convergence(mDataList);
        }

        axisScale();

//        mXAxisData.setAxisScale(mXAxisData.getAxisLength()/(mXAxisData.getMaximum()-mXAxisData.getMinimum()));
//        mYAxisData.setAxisScale(mYAxisData.getAxisLength()/(mYAxisData.getMaximum()-mYAxisData.getMinimum()));
//        mXAxisRender = new XAxisRender(mXAxisData);
//        mYAxisRender = new YAxisRender(mYAxisData,mXAxisData);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.translate(mViewWidth/2-mXAxisData.getAxisLength()/2,mViewHeight/2+mYAxisData.getAxisLength()/2);
        canvas.save();
        canvas.scale(1,-1);
        axisRender(canvas);
//        mXAxisRender.drawGraph(canvas);
//        mYAxisRender.drawGraph(canvas);
        canvas.restore();

        drawGraphical(canvas);
    }

    protected abstract void axisScale();

    protected abstract void axisRender(Canvas canvas);

    @Override
    public void computeXAxis() {
        mComputeXAxis.computeXAxis(mDataList);
    }

    @Override
    public void computeYAxis() {
        mComputeYAxis.computeYAxis(mDataList);
    }

    @Override
    public void setData(T chartData) {
        mDataList.clear();
        mDataList.add(chartData);
        computeXAxis();
        computeYAxis();
    }

    public void setDataList(ArrayList<T> chartDataList) {
        mDataList = chartDataList;
        computeXAxis();
        computeYAxis();
    }

    protected abstract void animated();

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

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }
}
