package com.idtk.smallchart.render;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;

import com.idtk.smallchart.data.PointData;

import java.text.NumberFormat;

/**
 * Created by Idtk on 2016/6/8.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 ; 点形状渲染类
 */
public class PointRender extends Render {

    private PointF mPointF = new PointF();
    private Paint mPaint = new Paint();
    private Paint.FontMetrics fontMetrics;
    private NumberFormat numberFormatY;
    private Xfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER);

    public PointRender() {
        super();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setXfermode(mXfermode);
    }

    public void drawCirclePoint(Canvas canvas, PointF pointF, PointData pointData,int textSize,PointF pointF2){
        mPointF.x = pointF.x;
        mPointF.y = -pointF.y;
        switch (pointData.getPointShape()){
            case CIRCLE:
                canvas.drawCircle(pointF.x,pointF.y,pointData.getOutRadius(),pointData.getOutPaint());
                canvas.drawCircle(pointF.x,pointF.y,pointData.getInRadius(),pointData.getInPaint());
                break;
            case RECT:
                break;
            case TRIANGLE:
                break;
        }
        numberFormatY = NumberFormat.getNumberInstance();
        numberFormatY.setMaximumFractionDigits(0);
        mPaint.setTextSize(textSize);
        fontMetrics= mPaint.getFontMetrics();
        mPointF.y = -mPointF.y+(fontMetrics.top-fontMetrics.bottom);
        textCenter(new String[]{numberFormatY.format(pointF2.y)},mPaint,canvas,mPointF, Paint.Align.CENTER);
    }
}
