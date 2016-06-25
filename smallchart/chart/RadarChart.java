package com.idtk.smallchart.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;

import com.idtk.smallchart.compute.ComputeRadar;
import com.idtk.smallchart.data.RadarAxisData;
import com.idtk.smallchart.interfaces.iChart.IRadarChart;
import com.idtk.smallchart.interfaces.iData.IRadarAxisData;
import com.idtk.smallchart.interfaces.iData.IRadarData;
import com.idtk.smallchart.render.ChartRender;
import com.idtk.smallchart.render.RadarAxisRender;
import com.idtk.smallchart.render.RadarChartRender;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by Idtk on 2016/6/20.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 雷达图绘制类
 */
public class RadarChart extends PieRadarChart<IRadarData> implements IRadarChart {

    protected IRadarAxisData mRadarAxisData = new RadarAxisData();
    protected ComputeRadar mComputeRadar = new ComputeRadar(mRadarAxisData);
    private RadarAxisRender mRadarAxisRender = new RadarAxisRender(mRadarAxisData);
    private RadarChartRender mRadarChartRender;
    protected float radius;
    /**
     * 获取个角度cos sin
     */
    private PathMeasure measure = new PathMeasure();
    private float[] pos = new float[2];
    private float[] tan = new float[2];
    private Path mPath = new Path();

    public RadarChart(Context context) {
        super(context);
    }

    public RadarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RadarChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        radius = Math.min(mWidth,mHeight)*0.35f;
        mRadarAxisData.setAxisLength(radius);
        mRadarAxisData.setAxisScale(radius/(mRadarAxisData.getMaximum()-mRadarAxisData.getMinimum()));

        mPath.addCircle(0,0,mRadarAxisData.getAxisLength(), Path.Direction.CW);
        measure.setPath(mPath,true);
        float[] cosArray = new float[mRadarAxisData.getTypes().length];
        float[] sinArray = new float[mRadarAxisData.getTypes().length];
        for (int i=0; i<mRadarAxisData.getTypes().length; i++){
            measure.getPosTan((float) (Math.PI*2*mRadarAxisData.getAxisLength()*i/mRadarAxisData.getTypes().length),pos,tan);
            cosArray[i] = tan[0];
            sinArray[i] = tan[1];

        }
        mPath.reset();

        mRadarAxisData.setCosArray(cosArray);
        mRadarAxisData.setSinArray(sinArray);

        chartRenderList.clear();
        for (IRadarData data : mDataList){
            mRadarChartRender = new RadarChartRender(data,mRadarAxisData);
            chartRenderList.add(mRadarChartRender);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.translate(mViewWidth/2,mViewHeight/2);
        canvas.save();
        canvas.rotate(-180);
        for (ChartRender chartRender : chartRenderList){
            chartRender.drawGraph(canvas,0);
        }
        canvas.restore();
        mRadarAxisRender.drawGraph(canvas);
    }

    @Override
    public void setData(IRadarData chartData) {
        super.setData(chartData);
        computeRadar();
    }

    @Override
    public void setDataList(ArrayList<IRadarData> chartDataList) {
        super.setDataList(chartDataList);
        computeRadar();
    }

    @Override
    public int getCurrentWidth() {
        int wrapSize;
        if (mDataList!=null&&mDataList.size()>1&&mRadarAxisData.getTypes().length>1){
            NumberFormat numberFormat =NumberFormat.getPercentInstance();
            numberFormat.setMinimumFractionDigits(mRadarAxisData.getDecimalPlaces());
            paintText.setStrokeWidth(mRadarAxisData.getPaintWidth());
            paintText.setTextSize(mRadarAxisData.getTextSize());
            Paint.FontMetrics fontMetrics= paintText.getFontMetrics();
            float top = fontMetrics.top;
            float bottom = fontMetrics.bottom;
            float webWidth = (bottom-top)*(float) Math.ceil((mRadarAxisData.getMaximum()-mRadarAxisData.getMinimum())
                    /mRadarAxisData.getInterval());
            float nameWidth = paintText.measureText(mRadarAxisData.getTypes()[0]);
            wrapSize = (int) (webWidth*2+nameWidth*1.1);
        }else {
            wrapSize = 0;
        }
        return wrapSize;
    }

    @Override
    public int getCurrentHeight() {
        int wrapSize;
        if (mDataList!=null&&mDataList.size()>1&&mRadarAxisData.getTypes().length>1){
            NumberFormat numberFormat =NumberFormat.getPercentInstance();
            numberFormat.setMinimumFractionDigits(mRadarAxisData.getDecimalPlaces());
            paintText.setStrokeWidth(mRadarAxisData.getPaintWidth());
            paintText.setTextSize(mRadarAxisData.getTextSize());
            Paint.FontMetrics fontMetrics= paintText.getFontMetrics();
            float top = fontMetrics.top;
            float bottom = fontMetrics.bottom;
            float webWidth = (bottom-top)*(float) Math.ceil((mRadarAxisData.getMaximum()-mRadarAxisData.getMinimum())
                    /mRadarAxisData.getInterval());
            float nameWidth = paintText.measureText(mRadarAxisData.getTypes()[0]);
            wrapSize = (int) (webWidth*2+nameWidth*1.1);
        }else {
            wrapSize = 0;
        }
        return wrapSize;
    }

    public void computeRadar() {
        mComputeRadar.computeRadar(mDataList);
    }

    public void setAxisWidth(float webWidth) {
        mRadarAxisData.setPaintWidth(webWidth);
    }

    public void setTypes(String[] types) {
        mRadarAxisData.setTypes(types);
    }

    public void setAxisTextSize(float textSize){
        mRadarAxisData.setTextSize(textSize);
    }

    public void setAxisValueColor(int color){
        mRadarAxisData.setColor(color);
    }

    public void setAxisColor(int color){
        mRadarAxisData.setWebColor(color);
    }



}
