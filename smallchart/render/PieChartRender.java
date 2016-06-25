package com.idtk.smallchart.render;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;

import com.idtk.smallchart.interfaces.iData.IPieAxisData;
import com.idtk.smallchart.interfaces.iData.IPieData;
import com.idtk.smallchart.interfaces.listener.TouchListener;

import java.text.NumberFormat;

/**
 * Created by Idtk on 2016/6/22.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 扇形图渲染类
 */
public class PieChartRender extends ChartRender implements TouchListener{

    private IPieAxisData pieAxisData;
    private IPieData pieData;
    private Paint mPaint = new Paint(),paintText = new Paint();
    //引入Path
    private Path outPath = new Path();
    private Path midPath = new Path();
    private Path inPath = new Path();
    private Path outMidPath = new Path();
    private Path midInPath = new Path();
    private float drawAngle;
    private NumberFormat numberFormat;
    private PointF mPointF = new PointF();
    private boolean touchFlag = false;

    public PieChartRender(IPieAxisData pieAxisData,IPieData pieData) {
//        super();
        this.pieAxisData = pieAxisData;
        this.pieData = pieData;

        paintText.setAntiAlias(true);
        paintText.setColor(pieAxisData.getColor());
        paintText.setTextSize(pieAxisData.getTextSize());
        paintText.setTextAlign(Paint.Align.CENTER);
        paintText.setStrokeWidth(pieAxisData.getPaintWidth());
        numberFormat =NumberFormat.getPercentInstance();
        numberFormat.setMinimumFractionDigits(pieAxisData.getDecimalPlaces());
    }

    @Override
    public void drawGraph(Canvas canvas, float animatedValue) {
        /**
         * 绘制视图
         */
        if (Math.min(pieData.getAngle()-1,animatedValue-pieData.getCurrentAngle())>=0){
            drawAngle = Math.min(pieData.getAngle()-1,animatedValue-pieData.getCurrentAngle());
        }else {
            drawAngle = 0;
        }
        if (touchFlag){
            drawArc(canvas,pieData.getCurrentAngle(),drawAngle,pieData,pieAxisData.getOffsetRectFs()[0],
                    pieAxisData.getOffsetRectFs()[1],pieAxisData.getOffsetRectFs()[2],mPaint);
        }else {
            drawArc(canvas,pieData.getCurrentAngle(),drawAngle,pieData,pieAxisData.getRectFs()[0],
                    pieAxisData.getRectFs()[1],pieAxisData.getRectFs()[2],mPaint);
        }

        /**
         * 绘制文字
         */
        canvas.save();
        canvas.rotate(-pieAxisData.getStartAngle());
        //根据Paint的TextSize计算Y轴的值
        if (pieAxisData.getIsTextSize()&&animatedValue>pieData.getCurrentAngle()-pieData.getAngle()/2) {
            if (touchFlag) {
                drawText(canvas,pieData,pieData.getCurrentAngle()+pieAxisData.getStartAngle(),numberFormat,true);
            } else {
                if (pieData.getAngle() > pieAxisData.getMinAngle()) {
                    drawText(canvas,pieData,pieData.getCurrentAngle()+pieAxisData.getStartAngle(),numberFormat,false);
                }
            }
        }
        canvas.restore();
    }

    private void drawArc(Canvas canvas, float currentStartAngle, float drawAngle, IPieData pie,
                         RectF outRectF, RectF midRectF, RectF inRectF, Paint paint){
        outPath.moveTo(0,0);
        outPath.arcTo(outRectF,currentStartAngle,drawAngle);
        midPath.moveTo(0,0);
        midPath.arcTo(midRectF,currentStartAngle,drawAngle);
        inPath.moveTo(0,0);
        inPath.arcTo(inRectF,currentStartAngle,drawAngle);
        outMidPath.op(outPath,midPath, Path.Op.DIFFERENCE);
        midInPath.op(midPath,inPath, Path.Op.DIFFERENCE);
        paint.setColor(pie.getColor());
        canvas.drawPath(outMidPath,paint);
        paint.setAlpha(0x80);//设置透明度
        canvas.drawPath(midInPath,paint);
        outPath.reset();
        midPath.reset();
        inPath.reset();
        outMidPath.reset();
        midInPath.reset();
    }

    private void drawText(Canvas canvas, IPieData pie , float currentStartAngle, NumberFormat numberFormat, boolean flag){
        int textPathX = (int) (Math.cos(Math.toRadians(currentStartAngle + (pie.getAngle() / 2))) *
                pieAxisData.getAxisLength()*(1 + pieAxisData.getOutsideRadiusScale()) / 2);
        int textPathY = (int) (Math.sin(Math.toRadians(currentStartAngle + (pie.getAngle() / 2))) *
                pieAxisData.getAxisLength()*(1 + pieAxisData.getOutsideRadiusScale()) / 2);
        mPointF.x = textPathX;
        mPointF.y = textPathY;
        String[] strings;
        if (flag){
            strings = new String[]{pie.getName() + "", numberFormat.format(pie.getPercentage()) + ""};
        }else {
            strings = new String[]{numberFormat.format(pie.getPercentage()) + ""};
        }
        textCenter(strings, paintText, canvas, mPointF, Paint.Align.CENTER);
    }

    @Override
    public void setTouchFlag(boolean touchFlag) {
        this.touchFlag = touchFlag;
    }
}
