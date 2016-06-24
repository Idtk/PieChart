package com.idtk.smallchart.compute;

import com.idtk.smallchart.interfaces.iData.IPieAxisData;
import com.idtk.smallchart.interfaces.iData.IPieData;

import java.util.ArrayList;

/**
 * Created by Idtk on 2016/6/22.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 ; 扇形图数据计算类
 */
public class ComputePie {

    private IPieAxisData pieAxisData;
    private float[] startAngles;
    public ComputePie(IPieAxisData pieAxisData) {
        this.pieAxisData = pieAxisData;
    }

    public void computePie(ArrayList<IPieData> pieDataList){
        if (pieDataList ==null||pieDataList.size()==0)
            return;
        startAngles = new float[pieDataList.size()];
        float sumValue = 0;
        for (int i=0; i<pieDataList.size(); i++){
            IPieData pie = pieDataList.get(i);
            sumValue += pie.getValue();
        }

        float sumAngle = 0;
        for (int i=0; i<pieDataList.size();i++){
            IPieData pie = pieDataList.get(i);
            float percentage = pie.getValue()/sumValue;
            float angle = percentage*360;
            pie.setPercentage(percentage);
            pie.setAngle(angle);
            /**
             * 获取当前扇形区域的初始角度
             */
            pie.setCurrentAngle(sumAngle);
            sumAngle += angle;
            startAngles[i] = sumAngle;
        }
        pieAxisData.setStartAngles(startAngles);
    }
}
