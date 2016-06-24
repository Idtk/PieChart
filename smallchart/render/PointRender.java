package com.idtk.smallchart.render;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import com.idtk.smallchart.interfaces.iData.IPointData;

import java.text.NumberFormat;

/**
 * Created by Idtk on 2016/6/8.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 ; 点形状渲染类
 */
public class PointRender<T extends IPointData> extends Render {

    private PointF mPointF = new PointF();
    private Paint mPaint = new Paint();
    private Paint.FontMetrics fontMetrics;
    private NumberFormat numberFormatY;

    public PointRender() {
        super();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    public void drawCirclePoint(Canvas canvas, PointF pointF, PointF dataPointF, T pointData, float textSize, boolean isTextSize, int decimalPlaces){
        mPointF.x = pointF.x;
        mPointF.y = -pointF.y;
        switch (pointData.getPointShape()){
            case CIRCLE:
                canvas.drawCircle(pointF.x,pointF.y,pointData.getOutRadius(),pointData.getOutPaint());
                canvas.drawCircle(pointF.x,pointF.y,pointData.getInRadius(),pointData.getInPaint());
                break;
            case RECT:
                pointData.getOutPaint().setStrokeCap(Paint.Cap.SQUARE);
                pointData.getOutPaint().setStrokeWidth(pointData.getOutRadius()*2);
                canvas.drawPoint(pointF.x,pointF.y,pointData.getOutPaint());
                break;
            case SOLIDROUND:
                pointData.getOutPaint().setStrokeCap(Paint.Cap.ROUND);
                pointData.getOutPaint().setStrokeWidth(pointData.getOutRadius()*2);
                canvas.drawPoint(pointF.x,pointF.y,pointData.getOutPaint());
                break;
        }
        if (isTextSize){
            numberFormatY = NumberFormat.getNumberInstance();
            numberFormatY.setMaximumFractionDigits(decimalPlaces);
            mPaint.setTextSize(textSize);
            fontMetrics= mPaint.getFontMetrics();
            mPointF.y = -mPointF.y+(fontMetrics.top-fontMetrics.bottom);
            textCenter(new String[]{numberFormatY.format(dataPointF.y)},mPaint,canvas,mPointF, Paint.Align.CENTER);
        }
    }
}
