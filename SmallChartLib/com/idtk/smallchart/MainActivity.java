package com.idtk.smallchart;

import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.idtk.smallchart.chart.LineChart;
import com.idtk.smallchart.data.LineData;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private LineChart lineChart;
    private LineData mLineData = new LineData();
    private LineData mLineData2 = new LineData();
    private ArrayList<PointF> mPointArrayList = new ArrayList<>();
    private ArrayList<PointF> mPointArrayList2 = new ArrayList<>();
    private ArrayList<LineData> mLineDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        lineChart = (LineChart) findViewById(R.id.lineChart);
//        lineChart.setData(mLineData);
        lineChart.setDataList(mLineDatas);
    }

    private void initData(){
        for (int i=0; i<10; i++){
            mPointArrayList.add(new PointF(i*10,i*1f));
            mPointArrayList2.add(new PointF(i*0.1f,i*1f));
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
