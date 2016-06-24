package com.idtk.smallchart.render;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

import com.idtk.smallchart.interfaces.iData.IRadarAxisData;

import java.text.NumberFormat;

/**
 * Created by Idtk on 2016/6/21.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 :
 */
public class RadarAxisRender extends AxisRender {

    private Paint mPaintLine = new Paint(),mPaintText = new Paint();
    private Path mPathRing = new Path(),mPathLine = new Path();
    private PointF mPointF = new PointF(0,0);
    protected IRadarAxisData radarAxisData;

    public RadarAxisRender(IRadarAxisData radarAxisData) {
        this.radarAxisData = radarAxisData;
        mPaintLine.setAntiAlias(true);
        mPaintLine.setStyle(Paint.Style.STROKE);
        mPaintLine.setStrokeWidth(radarAxisData.getPaintWidth());
        mPaintLine.setColor(radarAxisData.getWebColor());

        mPaintText.setAntiAlias(true);
        mPaintText.setColor(radarAxisData.getColor());
        mPaintText.setStrokeWidth(radarAxisData.getPaintWidth());
        mPaintText.setTextSize(radarAxisData.getTextSize());
    }

    @Override
    public void drawGraph(Canvas canvas) {
        float number = (float) Math.ceil((radarAxisData.getMaximum()-radarAxisData.getMinimum())/radarAxisData.getInterval());
        canvas.save();
        canvas.rotate(-180);
        /**
         * 绘制圈
         */
        for (int i=0; i<number; i++){
            canvas.save();
            canvas.scale(1-i/number,1-i/number);
            mPathRing.moveTo(0,radarAxisData.getAxisLength());
            if (radarAxisData.getTypes()!=null)
                for (int j=0; j<radarAxisData.getTypes().length; j++){
                    mPathRing.lineTo(radarAxisData.getAxisLength()*radarAxisData.getCosArray()[j],
                            radarAxisData.getAxisLength()*radarAxisData.getSinArray()[j]);
                }
            mPathRing.close();
            canvas.drawPath(mPathRing,mPaintLine);
            mPathRing.reset();
            canvas.restore();
        }

        /**
         * 绘制连线、名字
         */
        if (radarAxisData.getTypes()!=null)
            for (int j=0; j<radarAxisData.getTypes().length; j++){
                mPathLine.moveTo(0,0);
                mPathLine.lineTo(radarAxisData.getAxisLength()*radarAxisData.getCosArray()[j],
                        radarAxisData.getAxisLength()*radarAxisData.getSinArray()[j]);
                canvas.save();
                canvas.rotate(180);
                mPointF.y = -radarAxisData.getAxisLength()*radarAxisData.getSinArray()[j]*1.1f;
                mPointF.x = -radarAxisData.getAxisLength()*radarAxisData.getCosArray()[j]*1.1f;
                if (radarAxisData.getCosArray()[j]>0.2){
                    textCenter(new String[]{radarAxisData.getTypes()[j]},mPaintText,canvas,mPointF, Paint.Align.RIGHT);
                }else if (radarAxisData.getCosArray()[j]<-0.2){
                    textCenter(new String[]{radarAxisData.getTypes()[j]},mPaintText,canvas,mPointF, Paint.Align.LEFT);
                }else {
                    textCenter(new String[]{radarAxisData.getTypes()[j]},mPaintText,canvas,mPointF, Paint.Align.CENTER);
                }
                canvas.restore();
            }
        mPathLine.close();
        canvas.drawPath(mPathLine,mPaintLine);
        mPathLine.reset();
        canvas.restore();

        /**
         * 绘制刻度
         */
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(radarAxisData.getDecimalPlaces());
        if (radarAxisData.getIsTextSize())
            for (int i=1; i<number+1; i++){
                mPointF.x = 0;
                mPointF.y = -radarAxisData.getAxisLength()*(1-i/number);
                canvas.drawText(numberFormat.format(radarAxisData.getMinimum()+radarAxisData.getInterval()*(number-i))
                        +" "+radarAxisData.getUnit(), mPointF.x, mPointF.y, mPaintText);
            }
    }
}
