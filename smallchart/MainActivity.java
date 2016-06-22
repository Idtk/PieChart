package com.idtk.smallchart;

import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.idtk.smallchart.chart.BarChart;
import com.idtk.smallchart.chart.CombineChart;
import com.idtk.smallchart.chart.CurveChart;
import com.idtk.smallchart.chart.LineChart;
import com.idtk.smallchart.chart.RadarChart;
import com.idtk.smallchart.data.BarData;
import com.idtk.smallchart.data.CurveData;
import com.idtk.smallchart.data.LineData;
import com.idtk.smallchart.data.PieData;
import com.idtk.smallchart.data.PointShape;
import com.idtk.smallchart.data.RadarData;
import com.idtk.smallchart.interfaces.IData.IBarLineCurveData;
import com.idtk.smallchart.interfaces.IData.IPieData;
import com.idtk.smallchart.interfaces.IData.IRadarData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private LineChart lineChart;
    private CurveChart curveChart;
    private BarChart barChart;
    private CombineChart combineChart;
    private RadarChart radarChart;
    private LineData mLineData = new LineData();
    private BarData mLineData2 = new BarData();
    private CurveData mLineData3 = new CurveData();
    private BarData mLineData4 = new BarData();
    private RadarData mRadarData = new RadarData();
    private RadarData mRadarData2 = new RadarData();
    private ArrayList<PointF> mPointArrayList = new ArrayList<>();
    private ArrayList<PointF> mPointArrayList2 = new ArrayList<>();
    private ArrayList<PointF> mPointArrayList3 = new ArrayList<>();
    private ArrayList<PointF> mPointArrayList4 = new ArrayList<>();
    private ArrayList<IBarLineCurveData> mLineDatas = new ArrayList<>();

    private float[][] points = new float[][]{{1,10}, {2,47}, {3,11}, {4,38}, {5,9},{6,52}, {7,14}, {8,37}, {9,29}, {10,31}};
    private float[][] points2 = new float[][]{{1,29}, {2,13}, {3,51}, {4,20}, {5,19},{6,20}, {7,54}, {8,7}, {9,19}, {10,41}};
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    private ArrayList<IRadarData> radarDataList = new ArrayList<>();
    private ArrayList<Float> radarValue = new ArrayList<>();
    private ArrayList<Float> radarValue2 = new ArrayList<>();

    private ArrayList<IPieData> mPieDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        classForName2();
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
//        combineChart = (CombineChart) findViewById(R.id.combineChart);
//        combineChart.isAnimated = false;
//        combineChart.setDataList(mLineDatas);

        radarChart = (RadarChart) findViewById(R.id.radarChart);
        radarChart.setDataList(radarDataList);
        radarChart.setTypes(new String[]{"Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H"});
    }

    private void initData2(){
        for (int i=0; i<9; i++){
            PieData pieData = new PieData();
            pieData.setName("区域"+i);
            pieData.setValue((float)i+1);
            pieData.setColor(mColors[i]);
            Log.i("pieData",pieData.getValue()+"");
            mPieDataList.add(pieData);
        }
    }

    private void initData(){
//        mPointArrayList.add(new PointF(-1,-1));
        for (int i=0; i<8; i++){
            mPointArrayList.add(new PointF(points[i][0],points[i][1]));
            mPointArrayList2.add(new PointF(points[i][0],points[i][1]+10));
            mPointArrayList3.add(new PointF(points[i][0],points[i][1]+20));
            mPointArrayList4.add(new PointF(points[i][0],points[i][1]+15));
            radarValue.add(points2[i][1]);
            radarValue2.add(points[i][1]);
        }
        mLineData.setValue(mPointArrayList);
        mLineData.setColor(Color.YELLOW);
//        mLineData.setName("LineChart");
        mLineData.setPaintWidth(5);
        mLineData.setTextSize(30);
        mLineData.setPointShape(PointShape.RECT);
        mLineData.setOutRadius(10);
        mLineData.setIsTextSize(false);

        mLineData2.setValue(mPointArrayList2);
        mLineData2.setColor(Color.CYAN);
//        mLineData2.setName("LineChart");
        mLineData2.setPaintWidth(5);
        mLineData2.setTextSize(30);

        mLineData3.setValue(mPointArrayList3);
        mLineData3.setColor(Color.MAGENTA);
//        mLineData3.setName("LineChart");
        mLineData3.setPaintWidth(5);
        mLineData3.setIntensity(0.2f);
        mLineData3.setTextSize(30);
        mLineData3.setPointShape(PointShape.SOLIDROUND);

        mLineData4.setValue(mPointArrayList4);
        mLineData4.setColor(Color.GRAY);
        mLineData4.setTextSize(30);


        mLineDatas.add(mLineData);
        mLineDatas.add(mLineData2);
        mLineDatas.add(mLineData3);
//        mLineDatas.add(mLineData4);

        mRadarData.setValue(radarValue);
        mRadarData.setColor(Color.MAGENTA);
        mRadarData.setPaintWidth(5);
        mRadarData2.setValue(radarValue2);
        mRadarData2.setColor(Color.CYAN);
        mRadarData2.setPaintWidth(5);
        radarDataList.add(mRadarData);
        radarDataList.add(mRadarData2);

        /*Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float test2= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,15,metrics);*/
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
