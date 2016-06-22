package com.idtk.smallchart.render;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;

import com.idtk.smallchart.interfaces.IData.IRadarAxisData;

/**
 * Created by Idtk on 2016/6/21.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 :
 */
public class RadarAxisRender extends AxisRender {

//    protected float radius;
    private Paint mPaint = new Paint(),mPaint2 = new Paint();
    private Path mPath = new Path(),mPath2 = new Path(),mPath3 = new Path();
    private PathMeasure measure = new PathMeasure();
    private float[] pos = new float[2];
    private float[] tan = new float[2];
    private PointF mPointF = new PointF(0,0);
    protected IRadarAxisData radarAxisData;

    public RadarAxisRender(IRadarAxisData radarAxisData) {
        this.radarAxisData = radarAxisData;
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(radarAxisData.getPaintWidth());
        mPaint.setColor(radarAxisData.getWebColor());

        mPaint2.setAntiAlias(true);
        mPaint2.setColor(radarAxisData.getColor());
        mPaint2.setStrokeWidth(radarAxisData.getPaintWidth());
        mPaint2.setTextSize(radarAxisData.getTextSize());
    }

    @Override
    public void drawGraph(Canvas canvas) {
        mPath.addCircle(0,0,radarAxisData.getAxisLength(), Path.Direction.CW);
        measure.setPath(mPath,false);
        float number = (float) Math.ceil((radarAxisData.getMaximum()-radarAxisData.getMinimum())/radarAxisData.getInterval());
        canvas.save();
        canvas.rotate(-90);
        /**
         * 绘制圈
         */
        for (int i=0; i<number; i++){
            canvas.save();
            canvas.scale(1-i/number,1-i/number);
            mPath2.moveTo(radarAxisData.getAxisLength(),0);
            if (radarAxisData.getTypes()!=null)
                for (int j=0; j<radarAxisData.getTypes().length; j++){
                    measure.getPosTan((float) (Math.PI*2*radarAxisData.getAxisLength()*j/radarAxisData.getTypes().length),pos,tan);
                    mPath2.lineTo(pos[0],pos[1]);
                }
            mPath2.close();
            canvas.drawPath(mPath2,mPaint);
            mPath2.reset();
            canvas.restore();
        }

        /**
         * 绘制连线、名字
         */
        if (radarAxisData.getTypes()!=null)
            for (int j=0; j<radarAxisData.getTypes().length; j++){
                measure.getPosTan((float) (Math.PI*2*radarAxisData.getAxisLength()*j/radarAxisData.getTypes().length),pos,tan);
                mPath3.moveTo(0,0);
                mPath3.lineTo(pos[0],pos[1]);
//                LogUtil.d("TAG",pos[0]+":"+pos[1]+":"+tan[0]+":"+tan[1]);
//                LogUtil.d("TAG",Math.tan(0)+":"+Math.tan(Math.toRadians(-45))+":"+Math.tan(Math.toRadians(-90))+":"+Math.tan(Math.toRadians(-135))+":"+Math.tan(Math.toRadians(180)));
                canvas.save();
                canvas.rotate(90);
                if (tan[0]>0.2){
                    mPointF.x = pos[1]*1.1f;
                    mPointF.y = -pos[0]*1.1f;
                    textCenter(new String[]{radarAxisData.getTypes()[j]},mPaint2,canvas,mPointF, Paint.Align.RIGHT);
                }else if (tan[0]<-0.2){
                    mPointF.x = pos[1]*1.1f;
                    mPointF.y = -pos[0]*1.1f;
                    textCenter(new String[]{radarAxisData.getTypes()[j]},mPaint2,canvas,mPointF, Paint.Align.LEFT);
                }else {
                    mPointF.x = pos[1]*1.1f;
                    mPointF.y = -pos[0]*1.1f;
                    textCenter(new String[]{radarAxisData.getTypes()[j]},mPaint2,canvas,mPointF, Paint.Align.CENTER);
                }
                canvas.restore();
            }
        mPath3.close();
        canvas.drawPath(mPath3,mPaint);
        mPath3.reset();
        mPath.reset();
        canvas.restore();

        /**
         * 绘制刻度
         */
        if (radarAxisData.getIsTextSize())
            for (int i=1; i<number+1; i++){
                mPointF.x = 0;
                mPointF.y = -radarAxisData.getAxisLength()*(1-i/number);
                canvas.drawText(radarAxisData.getMinimum()+radarAxisData.getInterval()*(number-i)
                        +" "+radarAxisData.getUnit(), mPointF.x, mPointF.y, mPaint2);
            }
    }
}
