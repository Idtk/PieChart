package com.idtk.smallchart;

import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.idtk.smallchart.chart.CurveChart;
import com.idtk.smallchart.chart.LineChart;
import com.idtk.smallchart.data.CurveData;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private LineChart lineChart;
    private CurveChart curveChart;
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
//        lineChart.setDataList(mLineDatas);

        curveChart = (CurveChart) findViewById(R.id.curveChart);
        curveChart.setDataList(mLineDatas);
    }

    private void initData(){
//        mPointArrayList.add(new PointF(-1,-1));
        for (int i=0; i<10; i++){
            mPointArrayList.add(new PointF(-i*100f,-i*100+100));
            mPointArrayList2.add(new PointF(-i*100+100,-i*100f));
        }
        mLineData.setValue(mPointArrayList);
        mLineData.setColor(Color.BLUE);
        mLineData.setName("LineChart");
        mLineData.setPaintWidth(5);
        mLineData2.setValue(mPointArrayList2);
        mLineData2.setColor(Color.GREEN);
        mLineData2.setName("LineChart");
        mLineData2.setPaintWidth(5);
        mLineDatas.add(mLineData);
        mLineDatas.add(mLineData2);
    }
}
