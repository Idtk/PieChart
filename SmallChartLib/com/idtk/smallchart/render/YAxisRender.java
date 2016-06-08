package com.idtk.smallchart.render;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import com.idtk.smallchart.data.XAxisData;
import com.idtk.smallchart.data.YAxisData;

import java.text.NumberFormat;

/**
 * Created by Idtk on 2016/6/6.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 */
public class YAxisRender extends Render {
    private Paint mPaint = new Paint();
    private Paint linePaint = new Paint();
    private YAxisData yAxisData = new YAxisData();
    private XAxisData xAxisData = new XAxisData();
    private NumberFormat numberFormat;
    private PointF mPoint = new PointF();

    public YAxisRender(YAxisData yAxisData, XAxisData xAxisData) {
        super();
        this.yAxisData = yAxisData;
        this.xAxisData = xAxisData;
        mPaint.setAntiAlias(true);
        mPaint.setColor(yAxisData.getColor());
        mPaint.setTextSize(yAxisData.getTextSize());
        mPaint.setStrokeWidth(yAxisData.getPaintWidth());

        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.BLACK);

        //设置小数点位数
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(yAxisData.getDecimalPlaces());
    }

    @Override
    public void drawGraph(Canvas canvas) {
//        canvas.drawText("YAxisRender",0,0,paint);
        canvas.drawLine(0,0,0,yAxisData.getAxisLength(),mPaint);
        //Y轴
        for (int i=0;(yAxisData.getInterval()*i+yAxisData.getMinimum())<=yAxisData.getMaximum();i++){
            //坐标轴刻度
            canvas.save();
            canvas.scale(1,-1);
            //根据Paint的TextSize计算X轴的值
            float TextPathX = yAxisData.getAxisLength()/100;
            float TextPathY = (mPaint.descent()+mPaint.ascent())/2+(float) (yAxisData.getInterval()*i*yAxisData.getAxisScale());
//            canvas.drawText(numberFormat.format(yAxisData.getInterval()*i+yAxisData.getMinimum()),-TextPathX,-TextPathY,mPaint);
            mPoint.x =-TextPathX;
            mPoint.y =-TextPathY;
            textCenter(new String[]{numberFormat.format(yAxisData.getInterval()*i+yAxisData.getMinimum())},
                    mPaint,canvas, mPoint, Paint.Align.RIGHT);
            if (i>0)
            canvas.drawLine(0,-TextPathY,xAxisData.getAxisLength(),-TextPathY,linePaint);
            canvas.restore();
        }
        canvas.drawLine(0,yAxisData.getAxisLength(),yAxisData.getAxisLength()*0.01f,yAxisData.getAxisLength()*0.99f,mPaint);
        canvas.drawLine(0,yAxisData.getAxisLength(),-yAxisData.getAxisLength()*0.01f,yAxisData.getAxisLength()*0.99f,mPaint);
        canvas.save();
        canvas.scale(1,-1);
        canvas.drawText(yAxisData.getUnit(),0,-yAxisData.getAxisLength()+(mPaint.descent()+mPaint.ascent())*2,mPaint);
        canvas.restore();
    }
}
