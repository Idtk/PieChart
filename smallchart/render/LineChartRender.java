package com.idtk.smallchart.render;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

import com.idtk.smallchart.interfaces.iData.ILineData;
import com.idtk.smallchart.interfaces.iData.IPointData;
import com.idtk.smallchart.interfaces.iData.IXAxisData;
import com.idtk.smallchart.interfaces.iData.IYAxisData;

import java.util.ArrayList;

/**
 * Created by Idtk on 2016/6/6.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 ; 折线图渲染类
 */
public class LineChartRender extends ChartRender {

    private ILineData lineData;
    private Path mPath = new Path();
    private Paint linePaint = new Paint();
    private IXAxisData xAxisData;
    private IYAxisData yAxisData;
    private ArrayList<PointF> pointList = new ArrayList<>();
    private PointRender mPointRender = new PointRender();

    private Paint outpointPaint = new Paint();
    private Paint inPointPaint = new Paint();

    private float offset;


    public LineChartRender(ILineData lineData,IXAxisData xAxisData, IYAxisData yAxisData,float offset) {
        super();
        this.lineData = lineData;
        this.xAxisData = xAxisData;
        this.yAxisData = yAxisData;
        this.offset = offset;
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true);
        outpointPaint.setStyle(Paint.Style.FILL);
        outpointPaint.setAntiAlias(true);
        inPointPaint.setStyle(Paint.Style.FILL);
        inPointPaint.setAntiAlias(true);
        linePaint.setStrokeWidth(lineData.getPaintWidth());
        outpointPaint.setStrokeWidth(linePaint.getStrokeWidth());
        inPointPaint.setStrokeWidth(linePaint.getStrokeWidth());
        linePaint.setColor(lineData.getColor());
    }


    @Override
    public void drawGraph(Canvas canvas, float animatedValue) {
//        canvas.drawText("LineChartRender",0,0,paint);

        mPath.incReserve(lineData.getValue().size());//为添加更多点准备路径,可以更有效地分配其存储的路径
        pointList.clear();
        for (int j=0; j< lineData.getValue().size(); j++){
            float currentYAxis,currentXAxis;
            /**
             * 动画计算
             */
            if (animatedValue<0.5){
                currentYAxis = animatedValue*yAxisData.getAxisLength();
            }else {
                if ((lineData.getValue().get(j).y-yAxisData.getMinimum())*yAxisData.getAxisScale() >= yAxisData.getAxisLength()/2){
                    switch (lineData.getLineAnimated()){
                        case SYNC://同时到达
                            currentYAxis = yAxisData.getAxisLength()/2+((lineData.getValue().get(j).y-yAxisData.getMinimum())*yAxisData.getAxisScale()
                                    -yAxisData.getAxisLength()/2)/yAxisData.getAxisLength()*2*(animatedValue*yAxisData.getAxisLength()-yAxisData.getAxisLength()/2);
                            break;
                        default://先后到达
                            currentYAxis = Math.min(animatedValue*yAxisData.getAxisLength(),
                                    (lineData.getValue().get(j).y-yAxisData.getMinimum())*yAxisData.getAxisScale());
                            break;
                    }
                }else {
                    switch (lineData.getLineAnimated()){
                        case SYNC:
                            currentYAxis = yAxisData.getAxisLength()/2-(yAxisData.getAxisLength()/2-
                                    (lineData.getValue().get(j).y-yAxisData.getMinimum())*yAxisData.getAxisScale())/
                                    yAxisData.getAxisLength()*2*(animatedValue*yAxisData.getAxisLength()-yAxisData.getAxisLength()/2);
                            break;
                        default:
                            currentYAxis = Math.max(yAxisData.getAxisLength()-animatedValue*yAxisData.getAxisLength(),
                                    (lineData.getValue().get(j).y-yAxisData.getMinimum())*yAxisData.getAxisScale());
                            break;
                    }
                }
            }
            currentXAxis = (lineData.getValue().get(j).x-xAxisData.getMinimum())*xAxisData.getAxisScale();
            if (j==0){
                mPath.moveTo(currentXAxis, -currentYAxis);
            }else {
                mPath.lineTo(currentXAxis, -currentYAxis);
            }
            /**
             * 保存
             */
            pointList.add(new PointF(currentXAxis,-currentYAxis));
        }
        canvas.save();
        canvas.translate(offset,0);
        canvas.drawPath(mPath,linePaint);
        mPath.rewind();//清除

        /**
         * 点绘制
         */
        outpointPaint.setColor(lineData.getColor());
        inPointPaint.setColor(Color.WHITE);
        ((IPointData)lineData).setInPaint(inPointPaint);
        ((IPointData)lineData).setOutPaint(outpointPaint);
        if (((IPointData)lineData).getInRadius() == -1)
            ((IPointData)lineData).setInRadius(xAxisData.getAxisLength()/100);
        if (((IPointData)lineData).getOutRadius() == -1)
            ((IPointData)lineData).setOutRadius(xAxisData.getAxisLength()/70);
        for (int j=0; j<pointList.size(); j++) {
            mPointRender.drawCirclePoint(canvas, pointList.get(j),lineData.getValue().get(j),
                    (IPointData) lineData,lineData.getTextSize(),lineData.getIsTextSize(),yAxisData.getDecimalPlaces());
        }
        canvas.restore();
    }
}
