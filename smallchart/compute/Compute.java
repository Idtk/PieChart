package com.idtk.smallchart.compute;

import com.idtk.smallchart.interfaces.iData.IAxisData;

/**
 * Created by Idtk on 2016/6/6.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 坐标轴计算帮助类
 */
public class Compute {

    public Compute(IAxisData axisData) {
        super();
    }

    protected int minAxisSgin = 1;
    protected int maxAxisSgin = 1;
    protected int scalAxisSgin = 1;

    /**
     * 计算最大最小整数值
     * @param max 最大值
     * @param min 最小值
     * @param count 第几组数据
     */
    protected void initMaxMin(float max, float min, int count, IAxisData axisData){

        int number = 0;
        //判断刻度值精度
        if (max>1){
            while (max>10){
                max=max/10;
                number++;
            }
            max = (float) (Math.ceil(max*Math.pow(10,number)));
            min = (float) (Math.floor(min/Math.pow(10,number)*Math.pow(10,number)));
        }else {
            while (0<max&&max<1){
                max=max*10;
                number++;
            }
            max = (float) (Math.ceil(max*Math.pow(10,-number)));
            min = (float) (Math.floor(min/Math.pow(10,-number)*Math.pow(10,-number)));
        }

        min = min*minAxisSgin;
        max = max*maxAxisSgin;
        //装最大最小值
        if (count == 0){
            axisData.setMinimum(min);
            axisData.setMaximum(max);
        }else {
            axisData.setMinimum(axisData.getMinimum()>min?min:axisData.getMinimum());
            axisData.setMaximum(axisData.getMaximum()<max?max:axisData.getMaximum());
        }
        minAxisSgin = 1;
        maxAxisSgin = 1;
    }

    /**
     * 计算区间大小
     * @param min 最小值
     * @param max 最大值
     * @param length 数据长度
     */
    protected void initScaling(float min, float max,int length,IAxisData axisData){
        float scaling;
        int count = 0;
        /**
         * 初步计算刻度值,排除length=0,或scaling=0的情况
         */
        if (length<16&&length!=0){
            scaling = (max-min)/(length-1);
//            LogUtil.d("TAG",max+":"+min+":"+scaling+":"+length);
        }else {
            scaling = (max-min)/15;
        }
        if (scaling<0){
            scaling = -scaling;
            scalAxisSgin = -1;
        }
        //判断刻度值精度
        if (scaling>1){
            while (scaling>10){
                scaling=scaling/10;
                count++;
            }
            scaling = (float) (Math.ceil(scaling)*Math.pow(10,count));
        }else {
            while (0<scaling&&scaling<1){
                scaling=scaling*10;
                count++;
            }
            scaling = (float) (Math.ceil(scaling)*Math.pow(10,-count));
            axisData.setDecimalPlaces(count);
        }

        axisData.setInterval(scaling*scalAxisSgin);
        scalAxisSgin = 1;
    }
}
