package com.idtk.smallchart;

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.idtk.smallchart.chart.BarChart;
import com.idtk.smallchart.chart.CombineChart;
import com.idtk.smallchart.chart.CurveChart;
import com.idtk.smallchart.chart.LineChart;
import com.idtk.smallchart.data.BarData;
import com.idtk.smallchart.data.BarLineCurveData;
import com.idtk.smallchart.data.CurveData;
import com.idtk.smallchart.data.LineData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private LineChart lineChart;
    private CurveChart curveChart;
    private BarChart barChart;
    private CombineChart combineChart;
    private LineData mLineData = new LineData();
    private BarData mLineData2 = new BarData();
    private CurveData mLineData3 = new CurveData();
    private BarData mLineData4 = new BarData();
    private ArrayList<PointF> mPointArrayList = new ArrayList<>();
    private ArrayList<PointF> mPointArrayList2 = new ArrayList<>();
    private ArrayList<PointF> mPointArrayList3 = new ArrayList<>();
    private ArrayList<PointF> mPointArrayList4 = new ArrayList<>();
    private ArrayList<BarLineCurveData> mLineDatas = new ArrayList<>();

    private float[][] points = new float[][]{{1,40}, {2,47}, {3,11}, {4,38}, {5,21}, {7,14}, {8,37}, {9,29}, {10,31},{11,52}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        classForName2();
        initData();
//        lineChart = (LineChart) findViewById(R.id.lineChart);
//        lineChart.setData(mLineData);
//        lineChart.isAnimated = false;
//        lineChart.setDataList(mLineDatas);
//        lineChart.setAxisWidth(5);

//        lineChart.setPointInRadius(20);

//        curveChart = (CurveChart) findViewById(R.id.curveChart);
//        curveChart.isAnimated = false;
//        curveChart.setDataList(mLineDatas);

//        barChart = (BarChart) findViewById(R.id.barChart);
//        barChart.setDataList(mLineDatas);
//        LogUtil.d("TAG",mLineDatas.size()+"");
        combineChart = (CombineChart) findViewById(R.id.combineChart);
//        combineChart.isAnimated = false;
        combineChart.setDataList(mLineDatas);

//        TypedValue.complexToDimensionPixelSize(20,getResources().getDisplayMetrics());

    }

    private void initData(){
//        mPointArrayList.add(new PointF(-1,-1));
        for (int i=0; i<points.length; i++){
            mPointArrayList.add(new PointF(points[i][0],points[i][1]));
            mPointArrayList2.add(new PointF(points[i][0],points[i][1]+10));
            mPointArrayList3.add(new PointF(points[i][0],points[i][1]+20));
            mPointArrayList4.add(new PointF(points[i][0],points[i][1]+15));
        }
        mLineData.setValue(mPointArrayList);
        mLineData.setColor(Color.YELLOW);
//        mLineData.setName("LineChart");
        mLineData.setAnimatedMod(LineData.AnimatedMod.ASYNC);
        mLineData.setPaintWidth(5);
        mLineData.setTextSize(30);

        mLineData2.setValue(mPointArrayList2);
        mLineData2.setColor(Color.CYAN);
//        mLineData2.setName("LineChart");
        mLineData2.setPaintWidth(5);
        mLineData2.setTextSize(30);

        mLineData3.setValue(mPointArrayList3);
        mLineData3.setColor(Color.BLACK);
//        mLineData3.setName("LineChart");
        mLineData3.setPaintWidth(5);
        mLineData3.setIntensity(0.2f);
        mLineData3.setTextSize(30);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
        mLineData3.setDrawable(drawable);

        mLineData4.setValue(mPointArrayList4);
        mLineData4.setColor(Color.GRAY);
        mLineData4.setTextSize(30);


//        mLineDatas.add(mLineData);
//        mLineDatas.add(mLineData2);
        mLineDatas.add(mLineData3);
//        mLineDatas.add(mLineData4);
    }

    private static void classForName() {
        try {
            // 获取 Class 对象
            Class<?> clz = Class.forName("com.idtk.smallchart.chart.BarChart");
            // 通过 Class 对象获取 Constructor，Student 的构造函数有一个字符串参数
            // 因此这里需要传递参数的类型 ( Student 类见后面的代码 )
            Constructor<?> constructor = clz.getConstructor(String.class);
            // 通过 Constructor 来创建 Student 对象
            Object obj = constructor.newInstance("mr.simple");
            System.out.println(" obj :  " + obj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void classForName2() {
        try {
            // 获取 Class 对象
            Class<?> cl = Class.forName("com.idtk.smallchart.render.BarChartRender");

            Constructor<?>[] constructors = cl.getDeclaredConstructors();

            for (Constructor c: constructors){
                String name = c.getName();
                String modifiers = Modifier.toString(c.getModifiers());

                Class[] paramTypes = c.getParameterTypes();
                String params="";
                for (Class p:paramTypes){
                    params=params+":"+p.getName().toString();
                }

                LogUtil.d("TAG",name+"1:"+modifiers+"2:"+params);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
