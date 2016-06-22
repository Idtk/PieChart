package com.idtk.smallchart.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.idtk.smallchart.compute.ComputeRadar;
import com.idtk.smallchart.data.RadarAxisData;
import com.idtk.smallchart.interfaces.IChart.IRadarChart;
import com.idtk.smallchart.interfaces.IData.IRadarAxisData;
import com.idtk.smallchart.interfaces.IData.IRadarData;
import com.idtk.smallchart.render.ChartRender;
import com.idtk.smallchart.render.RadarAxisRender;
import com.idtk.smallchart.render.RadarChartRender;

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
    protected float radius;

    private RadarAxisRender mRadarAxisRender = new RadarAxisRender(mRadarAxisData);
    private RadarChartRender mRadarChartRender;

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
        chartRenderList.clear();
        for (IRadarData data : mDataList){
            mRadarChartRender = new RadarChartRender(data,mRadarAxisData);
            chartRenderList.add(mRadarChartRender);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mViewWidth/2,mViewHeight/2);
        canvas.save();
        canvas.rotate(-90);
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

    public void computeRadar() {
        mComputeRadar.computeRadar(mDataList);
    }

    public void setWebWidth(float webWidth) {
        mRadarAxisData.setPaintWidth(webWidth);
    }

    public void setTypes(String[] types) {
        mRadarAxisData.setTypes(types);
    }

    public void setWebTextSize(float textSize){
        mRadarAxisData.setTextSize(textSize);
    }

    public void setColor(int color){
        mRadarAxisData.setColor(color);
    }

    public void setWebColor(int color){
        mRadarAxisData.setWebColor(color);
    }
}
