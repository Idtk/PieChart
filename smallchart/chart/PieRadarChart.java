package com.idtk.smallchart.chart;

import android.content.Context;
import android.util.AttributeSet;

import com.idtk.smallchart.interfaces.iChart.IPieRadarChart;
import com.idtk.smallchart.interfaces.iData.IChartData;

import java.util.ArrayList;

/**
 * Created by Idtk on 2016/6/20.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 扇形图、雷达图绘制基类
 */
public abstract class PieRadarChart<T extends IChartData> extends Chart<T> implements IPieRadarChart{

//    protected ArrayList<ChartRender> chartRenderList = new ArrayList<>();

    public PieRadarChart(Context context) {
        super(context);
    }

    public PieRadarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PieRadarChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setData(T chartData) {
        mDataList.clear();
        mDataList.add(chartData);
    }

    @Override
    public void setDataList(ArrayList<T> chartDataList) {
        mDataList = chartDataList;
    }
}
