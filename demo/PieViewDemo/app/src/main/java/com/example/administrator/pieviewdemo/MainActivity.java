package com.example.administrator.pieviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.customview.PieData;
import com.example.administrator.customview.PieView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<PieData> mPieDatas = new ArrayList<>();
    // 颜色表
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initView(){
        //设置LinearLayout
        LinearLayout.LayoutParams  params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1);
        params.gravity = Gravity.CENTER;
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        //设置饼状图
        PieView mPieView = new PieView(this);
        mPieView.setStartAngle(0);
        mPieView.setPieData(mPieDatas);
        mPieView.setInvalidate();
        linearLayout.addView(mPieView,params);

        /*PieView mPieView1 = new PieView(this);
        mPieView1.setStartAngle(0);
        mPieView1.setPieData(mPieDatas);
        mPieView1.setInvalidate();
        linearLayout.addView(mPieView1,params);*/

        this.setContentView(linearLayout,params);
    }

    private void initData(){
        for (int i=0; i<9; i++){
            PieData pieData = new PieData();
            pieData.setValue(i+1);
            pieData.setColor(mColors[i]);
            Log.i("colorOld",Integer.toHexString(mColors[i]));
            mPieDatas.add(pieData);
        }
    }
}
