package com.idtk.smallchart.compute;

import com.idtk.smallchart.data.AxisData;

/**
 * Created by Idtk on 2016/6/6.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 */
public class Compute {

    public Compute(AxisData axisData) {
        super();
    }

    /**
     * 计算最大最小整数值
     * @param max 最大值
     * @param min 最小值
     * @param count 第几组数据
     */
    protected void initMaxMin(float max, float min, int count, AxisData axisData){
        int number = 0;
        //判断刻度值精度
        if (max>1){
            while (max>10){
                max=max/10;
                number++;
            }
            max = (float) (Math.ceil(max)*Math.pow(10,number));
            min = (float) (Math.floor(min/Math.pow(10,number))*Math.pow(10,number));
        }else {
            while (max<1){
                max=max*10;
                number++;
            }
            max = (float) (Math.ceil(max)*Math.pow(10,-number));
            min = (float) (Math.floor(min/Math.pow(10,-number))*Math.pow(10,-number));
        }
        //装最大最小值
        if (count == 0){
            axisData.setMinimum(min);
            axisData.setMaximum(max);
        }else {
            axisData.setMinimum(axisData.getMinimum()>min?min:axisData.getMinimum());
            axisData.setMaximum(axisData.getMaximum()<max?max:axisData.getMaximum());
        }
    }

    /**
     * 计算区间大小
     * @param min 最小值
     * @param max 最大值
     * @param length 数据长度
     */
    protected void initScaling(float min, float max,int length,AxisData axisData){
        float scaling;
        int count = 0;
        //初步计算刻度值
        if (length<11){
            scaling = (max-min)/(length-1);
        }else {
            scaling = (max-min)/10;
        }
        //判断刻度值精度
        if (scaling>1){
            while (scaling>10){
                scaling=scaling/10;
                count++;
            }
            scaling = (float) (Math.ceil(scaling)*Math.pow(10,count));
        }else {
            while (scaling<1){
                scaling=scaling*10;
                count++;
            }
            scaling = (float) (Math.ceil(scaling)*Math.pow(10,-count));
            axisData.setDecimalPlaces(count);
        }

        axisData.setInterval(scaling);
    }
}
