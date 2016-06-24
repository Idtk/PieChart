package com.idtk.smallchart.render;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.idtk.smallchart.interfaces.iData.IRadarAxisData;
import com.idtk.smallchart.interfaces.iData.IRadarData;

/**
 * Created by Idtk on 2016/6/20.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 雷达图渲染类
 */
public class RadarChartRender extends ChartRender {

    private Paint mPaintFill = new Paint(),mPaintStroke = new Paint();
    private Path mPath = new Path();
    private IRadarAxisData radarAxisData;
    private IRadarData radarData;

    public RadarChartRender(IRadarData radarData,IRadarAxisData radarAxisData) {
        this.radarData = radarData;
        this.radarAxisData = radarAxisData;
        mPaintFill.setAntiAlias(true);
        mPaintFill.setStyle(Paint.Style.FILL);

        mPaintStroke.setAntiAlias(true);
        mPaintStroke.setStyle(Paint.Style.STROKE);
        mPaintStroke.setStrokeWidth(radarData.getPaintWidth());
    }

    @Override
    public void drawGraph(Canvas canvas, float animatedValue) {
        for (int i=0 ; i<radarAxisData.getTypes().length; i++){
            if (i<radarData.getValue().size()) {
                float value = radarData.getValue().get(i);
                float yValue = (value-radarAxisData.getMinimum())*radarAxisData.getAxisScale();
                if (i==0){
                    mPath.moveTo(yValue*radarAxisData.getCosArray()[i],yValue*radarAxisData.getSinArray()[i]);
                }else {
                    mPath.lineTo(yValue*radarAxisData.getCosArray()[i],yValue*radarAxisData.getSinArray()[i]);
                }
            }else {
                mPath.lineTo(0,0);
            }
//            LogUtil.d("TAG",radarAxisData.getCosArray()[i]+":"+radarAxisData.getSinArray()[i]);
        }
        mPath.close();
        mPaintFill.setColor(radarData.getColor());
        mPaintFill.setAlpha(radarData.getAlpha());
        canvas.drawPath(mPath,mPaintFill);
        mPaintStroke.setColor(radarData.getColor());
        canvas.drawPath(mPath,mPaintStroke);
        mPath.reset();
    }
}
