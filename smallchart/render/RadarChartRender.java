package com.idtk.smallchart.render;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;

import com.idtk.smallchart.interfaces.IData.IRadarAxisData;
import com.idtk.smallchart.interfaces.IData.IRadarData;

/**
 * Created by Idtk on 2016/6/20.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 雷达图渲染类
 */
public class RadarChartRender extends ChartRender {

    private Paint mPaint = new Paint(),mPaint2 = new Paint();
    private Path mPath = new Path(),mPath2 = new Path();
    private PathMeasure measure = new PathMeasure();
    private float[] pos = new float[2];
    private float[] tan = new float[2];
    private IRadarAxisData radarAxisData;
    private IRadarData radarData;

    public RadarChartRender(IRadarData radarData,IRadarAxisData radarAxisData) {
        this.radarData = radarData;
        this.radarAxisData = radarAxisData;
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        mPaint2.setAntiAlias(true);
        mPaint2.setStyle(Paint.Style.STROKE);
        mPaint2.setStrokeWidth(radarData.getPaintWidth());
    }

    @Override
    public void drawGraph(Canvas canvas, float animatedValue) {
        for (int i=0 ; i<radarAxisData.getTypes().length; i++){
            if (i<radarData.getValue().size()) {
                float value = radarData.getValue().get(i);
                float yValue = (value-radarAxisData.getMinimum())*radarAxisData.getAxisScale();
                mPath.addCircle(0,0,yValue, Path.Direction.CW);
                measure.setPath(mPath,true);
                measure.getPosTan((float) (Math.PI*2*yValue*i/radarAxisData.getTypes().length),pos,tan);
                if (i==0){
                    mPath2.moveTo(pos[0],pos[1]);
                }else {
                    mPath2.lineTo(pos[0],pos[1]);
                }
                mPath.reset();
            }else {
                mPath2.lineTo(0,0);
            }
        }
        mPath2.close();
        /*canvas.saveLayerAlpha(-radarAxisData.getAxisLength(),-radarAxisData.getAxisLength(),
                radarAxisData.getAxisLength(),radarAxisData.getAxisLength(), 0x80, Canvas.ALL_SAVE_FLAG);*/
        mPaint.setColor(radarData.getColor());
        mPaint.setAlpha(radarData.getAlpha());
        canvas.drawPath(mPath2,mPaint);
//        canvas.restore();
        mPaint2.setColor(radarData.getColor());
        canvas.drawPath(mPath2,mPaint2);
        mPath2.reset();
    }
}
