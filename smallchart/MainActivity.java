package com.idtk.smallchart;

import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.idtk.smallchart.chart.BarChart;
import com.idtk.smallchart.chart.CurveChart;
import com.idtk.smallchart.chart.LineChart;
import com.idtk.smallchart.data.CurveData;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private LineChart lineChart;
    private CurveChart curveChart;
    private BarChart barChart;
    private CurveData mLineData = new CurveData();
    private CurveData mLineData2 = new CurveData();
    private ArrayList<PointF> mPointArrayList = new ArrayList<>();
    private ArrayList<PointF> mPointArrayList2 = new ArrayList<>();
    private ArrayList<CurveData> mLineDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
//        lineChart = (LineChart) findViewById(R.id.lineChart);
//        lineChart.setData(mLineData);
//        lineChart.isAnimated = false;
//        lineChart.setDataList(mLineDatas);
//        lineChart.setAxisWidth(5);

//        lineChart.setPointInRadius(20);

        curveChart = (CurveChart) findViewById(R.id.curveChart);
//        curveChart.isAnimated = false;
        curveChart.setDataList(mLineDatas);

//        barChart = (BarChart) findViewById(R.id.barChart);
//        barChart.setDataList(mLineDatas);
//        LogUtil.d("TAG",mLineDatas.size()+"");
    }

    private void initData(){
//        mPointArrayList.add(new PointF(-1,-1));
        for (int i=0; i<6; i++){
            mPointArrayList.add(new PointF(i,i+5));
            mPointArrayList2.add(new PointF(i,i+7));
        }
        mLineData.setValue(mPointArrayList);
        mLineData.setColor(Color.YELLOW);
        mLineData.setName("LineChart");
//        mLineData.setAnimatedMod(LineData.AnimatedMod.ASYNC);
        mLineData.setPaintWidth(5);
        mLineData2.setValue(mPointArrayList2);
        mLineData2.setColor(Color.CYAN);
        mLineData2.setName("LineChart");
        mLineData2.setPaintWidth(5);
        mLineDatas.add(mLineData);
        mLineDatas.add(mLineData2);
    }
}
