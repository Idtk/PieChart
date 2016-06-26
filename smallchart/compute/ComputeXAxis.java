package com.idtk.smallchart.compute;

import android.graphics.Paint;

import com.idtk.smallchart.interfaces.iData.IBarLineCurveData;
import com.idtk.smallchart.interfaces.iData.IXAxisData;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by Idtk on 2016/6/6.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 ; X轴计算类
 */
public class ComputeXAxis<T extends IBarLineCurveData> extends Compute {

    private IXAxisData xAxisData;
    private NumberFormat numberFormat;
    private Paint paint = new Paint();
    /**
     * 限制收敛次数
     */
    private int times=0;

    public ComputeXAxis(IXAxisData axisData) {
        super(axisData);
        xAxisData = axisData;
        paint.setColor(xAxisData.getColor());
        paint.setTextSize(xAxisData.getTextSize());
        paint.setStrokeWidth(xAxisData.getPaintWidth());
        //设置小数点位数
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(xAxisData.getDecimalPlaces());
    }
    /**
     * 计算坐标系
     * @param mBarLineCurveDatas 坐标集合
     */
    public void computeXAxis(ArrayList<T> mBarLineCurveDatas){
        for (int i=0; i<mBarLineCurveDatas.size();i++){
            IBarLineCurveData mBarLineCurveData = mBarLineCurveDatas.get(i);
            int length = mBarLineCurveData.getValue().size();
            float maxX =0;
            float minX =0;
            if (length>0){
                maxX = Math.max(mBarLineCurveData.getValue().get(length-1).x,mBarLineCurveData.getValue().get(0).x);
                minX = Math.min(mBarLineCurveData.getValue().get(length-1).x,mBarLineCurveData.getValue().get(0).x);
            }

            /*for (int j=0; j<mBarLineCurveData.getValue().length; j++){
                Log.i("TAG1",(mBarLineCurveData.getValue()[j][0])+":"+(mBarLineCurveData.getValue()[j][1])+":"+i);
            }*/
            if (maxX<0){
                maxX=-maxX;
                maxAxisSgin = -1;
            }
            if (minX<0){
                minX=-minX;
                minAxisSgin = -1;
            }

            if (i==0){
                xAxisData.setNarrowMin(minX*minAxisSgin);
                xAxisData.setNarrowMax(maxX*maxAxisSgin);
            }else {
                xAxisData.setNarrowMin(minX*minAxisSgin<xAxisData.getNarrowMin()?minX*minAxisSgin:xAxisData.getNarrowMin());
                xAxisData.setNarrowMax(maxX*maxAxisSgin>xAxisData.getNarrowMax()?maxX*maxAxisSgin:xAxisData.getNarrowMax());
            }

            initMaxMin(maxX,minX,i,xAxisData);
        }
        //默认所有的BarLineCurveData。getValue()长度相同
        if (mBarLineCurveDatas.size()>0)
        initScaling(xAxisData.getMinimum(),xAxisData.getMaximum(),mBarLineCurveDatas.get(0).getValue().size(),xAxisData);
    }

    /**
     * 收敛X轴
     * @param barLineCurveDatas  X轴数据
     */
    public void convergence(ArrayList<T> barLineCurveDatas){
        times++;
        int count = 0;
        int newCount = 0;
//        initScaling(xAxisData.getMinimum(),xAxisData.getMaximum(),BarLineCurveDatas.get(0).getValue().size(),xAxisData);
        //二次处理字符过长
        while ((xAxisData.getInterval()*count+xAxisData.getMinimum())<=xAxisData.getMaximum()){
            count++;
        }
        float stringLength = paint.measureText(numberFormat.format(xAxisData.getInterval()+xAxisData.getMinimum()));
        while (count*stringLength>xAxisData.getAxisLength()){
            count = count/2;
            newCount++;
        }
        xAxisData.setInterval(newCount!=0? xAxisData.getInterval()*newCount*2:xAxisData.getInterval());
        //收敛
        while (xAxisData.getNarrowMin()-xAxisData.getMinimum()>xAxisData.getInterval()){
            xAxisData.setMinimum(xAxisData.getMinimum()+xAxisData.getInterval());
        }

        while (xAxisData.getMaximum()-xAxisData.getNarrowMax()>xAxisData.getInterval()){
            xAxisData.setMaximum(xAxisData.getMaximum()-xAxisData.getInterval());
        }

        if (xAxisData.getMaximum()-xAxisData.getMinimum()<=(xAxisData.getInterval()*2)&&times<5){
            initScaling(xAxisData.getMinimum(),xAxisData.getMaximum(),barLineCurveDatas.get(0).getValue().size(),xAxisData);
            convergence(barLineCurveDatas);
        }
    }
}
