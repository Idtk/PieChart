package com.example.administrator.customviewdeom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.customviewdeom.PieChart.PieChart;
import com.example.administrator.customviewdeom.PieChart.PieData;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<PieData> mPieDatas = new ArrayList<>();
    // 颜色表
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        PieChart pieChart = (PieChart) findViewById(R.id.pieChart);
        pieChart.setPieData(mPieDatas);
    }

    private void initData(){
        for (int i=0; i<9; i++){
            PieData pieData = new PieData();
            pieData.setName("区域"+i);
            pieData.setValue((float)i+1);
            pieData.setColor(mColors[i]);
            mPieDatas.add(pieData);
        }
    }
}
