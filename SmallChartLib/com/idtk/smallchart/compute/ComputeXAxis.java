package com.idtk.smallchart.compute;

import android.graphics.Paint;

import com.idtk.smallchart.data.AxisData;
import com.idtk.smallchart.data.XAxisData;
import com.idtk.smallchart.interfaces.IData.IBarLineCurveData;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by Idtk on 2016/6/6.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 */
public class ComputeXAxis<T extends IBarLineCurveData> extends Compute {

    private XAxisData xAxisData = new XAxisData();
    private NumberFormat numberFormat;
    private Paint paint = new Paint();

    public ComputeXAxis(AxisData axisData) {
        super(axisData);
        xAxisData = (XAxisData) axisData;
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
            float maxX = Math.max(mBarLineCurveData.getValue().get(length-1).x,mBarLineCurveData.getValue().get(0).x);
            float minX = Math.min(mBarLineCurveData.getValue().get(length-1).x,mBarLineCurveData.getValue().get(0).x);
            initMaxMin(maxX,minX,i,xAxisData);

            /*for (int j=0; j<mBarLineCurveData.getValue().length; j++){
                Log.i("TAG1",(mBarLineCurveData.getValue()[j][0])+":"+(mBarLineCurveData.getValue()[j][1])+":"+i);
            }*/
//            xAxisData.setNarrowMin(minX);
//            xAxisData.setNarrowMax(maxX);
        }
        //默认所有的BarLineCurveData。getValue()长度相同
        initScaling(xAxisData.getMinimum(),xAxisData.getMaximum(),mBarLineCurveDatas.get(0).getValue().size(),xAxisData);
    }

    @Override
    protected void initMaxMin(float max, float min, int count, AxisData axisData) {
        super.initMaxMin(max, min, count, axisData);
    }

    @Override
    protected void initScaling(float min, float max, int length, AxisData axisData) {
        super.initScaling(min, max, length, axisData);
    }

    public void convergence(ArrayList<T> barLineCurveDatas){
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

        if (xAxisData.getMaximum()-xAxisData.getMinimum()<=(xAxisData.getInterval()*2)){
            initScaling(xAxisData.getMinimum(),xAxisData.getMaximum(),barLineCurveDatas.get(0).getValue().size(),xAxisData);
            convergence(barLineCurveDatas);
        }
    }
}
