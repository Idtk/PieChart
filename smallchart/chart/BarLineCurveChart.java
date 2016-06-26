package com.idtk.smallchart.chart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.idtk.smallchart.animation.ChartAnimator;
import com.idtk.smallchart.compute.ComputeXAxis;
import com.idtk.smallchart.compute.ComputeYAxis;
import com.idtk.smallchart.data.XAxisData;
import com.idtk.smallchart.data.YAxisData;
import com.idtk.smallchart.interfaces.iChart.IBarLineCurveChart;
import com.idtk.smallchart.interfaces.iData.IBarLineCurveData;
import com.idtk.smallchart.interfaces.iData.IXAxisData;
import com.idtk.smallchart.interfaces.iData.IYAxisData;
import com.idtk.smallchart.render.XAxisOffsetRender;
import com.idtk.smallchart.render.XAxisRender;
import com.idtk.smallchart.render.YAxisRender;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by Idtk on 2016/6/6.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 柱状图、折线图、曲线图绘制基类
 */
public abstract class BarLineCurveChart<T extends IBarLineCurveData> extends Chart<T> implements IBarLineCurveChart {

    protected IXAxisData mXAxisData = new XAxisData();
    protected IYAxisData mYAxisData = new YAxisData();
    protected ComputeXAxis mComputeXAxis = new ComputeXAxis(mXAxisData);
    protected ComputeYAxis mComputeYAxis = new ComputeYAxis(mYAxisData);

    public boolean convergenceFlag = true;

    protected XAxisOffsetRender mXAxisOffsetRender;
    protected XAxisRender mXAxisRender;
    protected YAxisRender mYAxisRender;


//    protected ArrayList<ChartRender> chartRenderList = new ArrayList<>();
    protected float animatedValue;
    public boolean isAnimated = true;
    protected ChartAnimator mChartAnimator;


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
        /*mXAxisData.setTextSize(axisTextSize);
        mYAxisData.setTextSize(axisTextSize);
        mXAxisData.setColor(axisColor);
        mYAxisData.setColor(axisColor);
        mXAxisData.setPaintWidth(axisWidth);
        mYAxisData.setPaintWidth(axisWidth);
        mXAxisData.setUnit(XAxisUnit);
        mYAxisData.setUnit(YAxisUnit);*/

        animated();

        if (convergenceFlag){
            /**
             * 循环收敛
             */
            mComputeXAxis.convergence(mDataList);
            mComputeYAxis.convergence(mDataList);
        }

        axisScale();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(mViewWidth/2-mXAxisData.getAxisLength()/2,mViewHeight/2+mYAxisData.getAxisLength()/2);
        canvas.save();
        canvas.scale(1,-1);
        axisRender(canvas);
        canvas.restore();
        drawGraphical(canvas);
    }
    /**
     * 计算坐标轴刻度长度与View长度的比
     */
    protected abstract void axisScale();

    /**
     * 渲染X、Y轴
     * @param canvas 画布
     */
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

    @Override
    public void setDataList(ArrayList<T> chartDataList) {
        mDataList = chartDataList;
        computeXAxis();
        computeYAxis();
    }

    /**
     * 动画方法，默认动画时间两秒，生成监听值animatedValue
     * 如果不使用动画，则直接设置监听值为1
     */
    protected void animated() {
        if (!isAnimated) {
            animatedValue = 1;
        } else {
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

    /**
     * 图表渲染方法
     * @param canvas 渲染绘制的画布
     */
    protected void drawGraphical(Canvas canvas) {
        for (int i=0; i<chartRenderList.size(); i++){
            chartRenderList.get(i).drawGraph(canvas,animatedValue);
        }
    }

    @Override
    public int getCurrentWidth() {
        convergenceFlag = false;
        int wrapSize;
        if (mDataList!=null&&mDataList.size()>0){
            NumberFormat numberFormat =NumberFormat.getPercentInstance();
            numberFormat.setMinimumFractionDigits(mXAxisData.getDecimalPlaces());
            paintText.setTextSize(mXAxisData.getTextSize());
            paintText.setStrokeWidth(mXAxisData.getPaintWidth());
            float xAxisWidth = paintText.measureText(numberFormat.format(mXAxisData.getMaximum()))
                    *(float) Math.ceil((mXAxisData.getMaximum()-mXAxisData.getMinimum())/mXAxisData.getInterval());
            wrapSize = (int) (xAxisWidth*1.2f);
        }else {
            wrapSize = 0;
        }
//        LogUtil.d("TAGX",mXAxisData.getMaximum()+"__"+mXAxisData.getMinimum()+"__"+mXAxisData.getInterval());
        return wrapSize;
    }

    @Override
    public int getCurrentHeight() {
        convergenceFlag =false;
        Paint paintValue = new Paint();
        int wrapSize;
        if (mDataList!=null&&mDataList.size()>0){
            NumberFormat numberFormat =NumberFormat.getPercentInstance();
            numberFormat.setMinimumFractionDigits(mYAxisData.getDecimalPlaces());
            paintText.setStrokeWidth(mYAxisData.getPaintWidth());
            paintText.setTextSize(mYAxisData.getTextSize());
            Paint.FontMetrics fontMetrics= paintText.getFontMetrics();
            float top = fontMetrics.top;
            float bottom = fontMetrics.bottom;
            float yAxisWidth = (bottom-top)*(float) Math.ceil((mYAxisData.getMaximum()-mYAxisData.getMinimum())
                    /mYAxisData.getInterval());
            paintValue.setStrokeWidth(mDataList.get(0).getPaintWidth());
            paintValue.setTextSize(mDataList.get(0).getTextSize());
            Paint.FontMetrics fontMetricsValue= paintValue.getFontMetrics();
            float valueWidth =  fontMetricsValue.bottom-fontMetricsValue.top;
            wrapSize = (int) (yAxisWidth*2f+valueWidth*2);
        }else {
            wrapSize = 0;
        }
//        LogUtil.d("TAGY",mYAxisData.getMaximum()+"__"+mYAxisData.getMinimum()+"__"+mYAxisData.getInterval()+"__"+mDataList.size());
        return wrapSize;
    }

    @Override
    public void setAxisTextSize(float axisTextSize) {
//        this.axisTextSize = axisTextSize;
        mXAxisData.setTextSize(axisTextSize);
        mYAxisData.setTextSize(axisTextSize);
    }

    @Override
    public void setAxisColor(int axisColor) {
//        this.axisColor = axisColor;
        mXAxisData.setColor(axisColor);
        mYAxisData.setColor(axisColor);
    }

    @Override
    public void setAxisWidth(float axisWidth) {
//        this.axisWidth = axisWidth;
        mXAxisData.setPaintWidth(axisWidth);
        mYAxisData.setPaintWidth(axisWidth);
    }

    @Override
    public void setXAxisUnit(String XAxisUnit) {
//        this.XAxisUnit = XAxisUnit;
        mXAxisData.setUnit(XAxisUnit);
    }

    @Override
    public void setYAxisUnit(String YAxisUnit) {
//        this.YAxisUnit = YAxisUnit;
        mYAxisData.setUnit(YAxisUnit);
    }

}
