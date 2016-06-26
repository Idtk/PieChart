package com.idtk.smallchart.compute;

import android.graphics.Paint;

import com.idtk.smallchart.interfaces.iData.IBarLineCurveData;
import com.idtk.smallchart.interfaces.iData.IYAxisData;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by Idtk on 2016/6/6.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 ： Y轴计算类
 */
public class ComputeYAxis<T extends IBarLineCurveData> extends Compute {

    protected IYAxisData yAxisData;
    private NumberFormat numberFormat;
    private Paint paint = new Paint();
    /**
     * 限制收敛次数
     */
    private int times=0;

    public ComputeYAxis(IYAxisData axisData) {
        super(axisData);
        yAxisData = axisData;
        paint.setColor(yAxisData.getColor());
        paint.setTextSize(yAxisData.getTextSize());
        paint.setStrokeWidth(yAxisData.getPaintWidth());
        //设置小数点位数
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(yAxisData.getDecimalPlaces());
    }

    /**
     * 计算坐标系
     * @param mBarLineCurveDatas 坐标集合
     */
    public void computeYAxis(ArrayList<T> mBarLineCurveDatas){
        for (int i=0; i<mBarLineCurveDatas.size();i++){
            IBarLineCurveData mBarLineCurveData = mBarLineCurveDatas.get(i);
            float maxY = mBarLineCurveData.getValue().get(0).y;
            float minY = mBarLineCurveData.getValue().get(0).y;
            initAxis(mBarLineCurveData,maxY,minY,i);

            /*for (int j=0; j<mBarLineCurveData.getValue().length; j++){
                Log.i("TAG1",(mBarLineCurveData.getValue()[j][0])+":"+(mBarLineCurveData.getValue()[j][1])+":"+i);
            }*/
        }
        //默认所有的BarLineCurveData。getValue()长度相同
        if (mBarLineCurveDatas.size()>0)
            initScaling(yAxisData.getMinimum(),yAxisData.getMaximum(),mBarLineCurveDatas.get(0).getValue().size(),yAxisData);
    }

    /**
     * 计算Y轴最大值、最小值,强制设置最小值为0
     * @param mBarLineCurveDatas 图表数据
     */
    public void computeYAxisMin(ArrayList<T> mBarLineCurveDatas){
        for (int i=0; i<mBarLineCurveDatas.size();i++){
            IBarLineCurveData mBarLineCurveData = mBarLineCurveDatas.get(i);
            float maxY = mBarLineCurveData.getValue().get(0).y;
            initAxis(mBarLineCurveData,maxY,0,i);

            /*for (int j=0; j<mBarLineCurveData.getValue().length; j++){
                Log.i("TAG1",(mBarLineCurveData.getValue()[j][0])+":"+(mBarLineCurveData.getValue()[j][1])+":"+i);
            }*/
        }
        //默认所有的BarLineCurveData。getValue()长度相同
        if (mBarLineCurveDatas.size()>0)
            initScaling(yAxisData.getMinimum(),yAxisData.getMaximum(),mBarLineCurveDatas.get(0).getValue().size(),yAxisData);
    }

    /**
     * * 计算Y轴数据
     * @param mBarLineCurveData 单条曲线点数据
     * @param max 最大值
     * @param min 最小值
     * @param count 第几组数据
     */
    private void initAxis(IBarLineCurveData mBarLineCurveData, float max, float min, int count){
        for (int i=1; i<mBarLineCurveData.getValue().size();i++){
            max = Math.max(mBarLineCurveData.getValue().get(i).y,max);
            min = Math.min(mBarLineCurveData.getValue().get(i).y,min);
        }

        if (max<0){
            max=-max;
            maxAxisSgin = -1;
        }
        if (min<0){
            min=-min;
            minAxisSgin = -1;
        }

        if (count==0){
            yAxisData.setNarrowMin(min*minAxisSgin);
            yAxisData.setNarrowMax(max*maxAxisSgin);
        }else {
            yAxisData.setNarrowMin(min*minAxisSgin<yAxisData.getNarrowMin()?min*minAxisSgin:yAxisData.getNarrowMin());
            yAxisData.setNarrowMax(max*maxAxisSgin>yAxisData.getNarrowMax()?max*maxAxisSgin:yAxisData.getNarrowMax());
        }
        initMaxMin(max,min,count,yAxisData);
    }

    /**
     * Y轴收敛方法
     * @param barLineCurveDatas 图表数据
     */
    public void convergence(ArrayList<T> barLineCurveDatas){
        times++;
        int count = 0;
        int newCount = 0;
//        initScaling(xAxisData.getMinimum(),xAxisData.getMaximum(),BarLineCurveDatas.get(0).getValue().size(),xAxisData);
        //二次处理字符过长
        while ((yAxisData.getInterval()*count+yAxisData.getMinimum())<=yAxisData.getMaximum()){
            count++;
        }
        Paint.FontMetrics fontMetrics= paint.getFontMetrics();
        float ascent = fontMetrics.ascent;
        float descent = fontMetrics.descent;
        float stringLength = descent-ascent;
        while (count*stringLength>yAxisData.getAxisLength()){
            count = count/2;
            newCount++;
        }
        yAxisData.setInterval(newCount!=0? yAxisData.getInterval()*newCount*2:yAxisData.getInterval());
        //收敛
        while (yAxisData.getNarrowMin()-yAxisData.getMinimum()>yAxisData.getInterval()){
            yAxisData.setMinimum(yAxisData.getMinimum()+yAxisData.getInterval());
        }

        while (yAxisData.getMaximum()-yAxisData.getNarrowMax()>yAxisData.getInterval()){
            yAxisData.setMaximum(yAxisData.getMaximum()-yAxisData.getInterval());
        }

        if (yAxisData.getMaximum()-yAxisData.getMinimum()<=(yAxisData.getInterval()*2)&&times<5){
            initScaling(yAxisData.getMinimum(),yAxisData.getMaximum(),barLineCurveDatas.get(0).getValue().size(),yAxisData);
            convergence(barLineCurveDatas);
        }
    }
}
