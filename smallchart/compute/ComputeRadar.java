package com.idtk.smallchart.compute;

import android.graphics.Paint;

import com.idtk.smallchart.interfaces.iData.IAxisData;
import com.idtk.smallchart.interfaces.iData.IRadarAxisData;
import com.idtk.smallchart.interfaces.iData.IRadarData;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by Idtk on 2016/6/20.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 :
 */
public class ComputeRadar extends Compute {

    protected IRadarAxisData radarAxisData;
    private NumberFormat numberFormat;
    private Paint paint = new Paint();

    public ComputeRadar(IRadarAxisData axisData) {
        super(axisData);
        radarAxisData = axisData;
        paint.setColor(radarAxisData.getColor());
        paint.setTextSize(radarAxisData.getTextSize());
        paint.setStrokeWidth(radarAxisData.getPaintWidth());
        //设置小数点位数
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(radarAxisData.getDecimalPlaces());
    }

    /**
     * 计算坐标系
     * @param radarDataList 坐标集合
     */
    public void computeRadar(ArrayList<IRadarData> radarDataList){
        int radarDataMaxSize=0;
        for (int i=0; i<radarDataList.size(); i++){
            IRadarData radarData = radarDataList.get(i);
            if (radarData.getValue().size()>0){
                float max = radarData.getValue().get(0);
                initAxis(radarData,max,0,i);
                radarDataMaxSize=Math.max(radarData.getValue().size(),radarDataMaxSize);
            }
        }
        //默认所有的BarLineCurveData。getValue()长度相同
        if (radarDataList.size()>0)
            initScaling(radarAxisData.getMinimum(),radarAxisData.getMaximum(),radarDataList.get(0).getValue().size(),radarAxisData);
    }

    /**
     * * 计算Y轴数据
     * @param radarData 单条曲线点数据
     * @param max 最大值
     * @param min 最小值
     */
    private void initAxis(IRadarData radarData, float max, float min, int count){
        for (float radarValue : radarData.getValue()){
            max = Math.max(radarValue,max);
            min = Math.min(radarValue,min);
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
            radarAxisData.setNarrowMin(min*minAxisSgin);
            radarAxisData.setNarrowMax(max*maxAxisSgin);
        }else {
            radarAxisData.setNarrowMin(min*minAxisSgin<radarAxisData.getNarrowMin()?min*minAxisSgin:radarAxisData.getNarrowMin());
            radarAxisData.setNarrowMax(max*maxAxisSgin>radarAxisData.getNarrowMax()?max*maxAxisSgin:radarAxisData.getNarrowMax());
        }
        initMaxMin(max,min,count,radarAxisData);
    }

    @Override
    protected void initScaling(float min, float max, int length, IAxisData axisData) {
        float scaling;
        int count = 0;
        /**
         * 初步计算刻度值,排除length=0,或scaling=0的情况
         */
        if (length<7&&length!=0){
            scaling = (max-min)/length;
//            LogUtil.d("TAG",max+":"+min+":"+scaling+":"+length);
        }else {
            scaling = (max-min)/6;
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
