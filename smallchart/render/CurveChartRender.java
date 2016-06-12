package com.idtk.smallchart.render;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

import com.idtk.smallchart.data.CurveData;
import com.idtk.smallchart.data.PointData;
import com.idtk.smallchart.data.XAxisData;
import com.idtk.smallchart.data.YAxisData;

import java.util.ArrayList;

/**
 * Created by Idtk on 2016/6/6.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 */
public class CurveChartRender extends ChartRender<CurveData> {

    private CurveData curveData;
    private Path cubicPath = new Path();
    private Paint cubicPaint = new Paint();
    private XAxisData xAxisData = new XAxisData();
    private YAxisData yAxisData = new YAxisData();
    private ArrayList<PointF> pointList = new ArrayList<>();
    private TouchPointRender mPointRender = new TouchPointRender();
    private PointData pointData;
    private Paint outpointPaint = new Paint();
    private Paint inPointPaint = new Paint();
    private float intensity;

    public CurveChartRender(CurveData curveData, XAxisData xAxisData, YAxisData yAxisData, PointData pointData) {
        super();
        this.curveData = curveData;
        this.xAxisData = xAxisData;
        this.yAxisData = yAxisData;
        this.pointData = pointData;
        cubicPaint.setStyle(Paint.Style.STROKE);
        cubicPaint.setAntiAlias(true);
        outpointPaint.setStyle(Paint.Style.FILL);
        outpointPaint.setAntiAlias(true);
        inPointPaint.setStyle(Paint.Style.FILL);
        inPointPaint.setAntiAlias(true);
        cubicPaint.setStrokeWidth(curveData.getPaintWidth());
        outpointPaint.setStrokeWidth(cubicPaint.getStrokeWidth());
        inPointPaint.setStrokeWidth(cubicPaint.getStrokeWidth());
        intensity = curveData.getIntensity();
    }

    @Override
    public void drawGraph(Canvas canvas, float animatedValue) {
        float prevDx = 0f;
        float prevDy = 0f;
        float curDx = 0f;
        float curDy = 0f;

        PointF prevPrev = curveData.getValue().get(0);
        PointF prev = prevPrev;
        PointF cur = prev;
        PointF next = curveData.getValue().get(1);

        cubicPath.moveTo((cur.x-xAxisData.getMinimum())*xAxisData.getAxisScale(),
                (cur.y-yAxisData.getMinimum())*yAxisData.getAxisScale()*animatedValue);

        for (int j=1; j< curveData.getValue().size(); j++){
            prevPrev = curveData.getValue().get(j == 1 ? 0 : j - 2);
            prev = curveData.getValue().get(j-1);
            cur = curveData.getValue().get(j);
            next = curveData.getValue().size() > j+1 ? curveData.getValue().get(j+1) : cur;

            prevDx = (float) ((cur.x-prevPrev.x)*intensity*xAxisData.getAxisScale());
            prevDy = (float) ((cur.y-prevPrev.y)*intensity*yAxisData.getAxisScale());
            curDx = (float) ((next.x-prev.x)*intensity*xAxisData.getAxisScale());
            curDy = (float) ((next.y-prev.y)*intensity*yAxisData.getAxisScale());

            cubicPath.cubicTo((float) ((prev.x-xAxisData.getMinimum())*xAxisData.getAxisScale()+prevDx),
                    (float) (((prev.y-yAxisData.getMinimum())*yAxisData.getAxisScale()+prevDy)*animatedValue),
                    (float) ((cur.x-xAxisData.getMinimum())*xAxisData.getAxisScale()-curDx),
                    (float) (((cur.y-yAxisData.getMinimum())*yAxisData.getAxisScale()-curDy)*animatedValue),
                    (float) ((cur.x-xAxisData.getMinimum())*xAxisData.getAxisScale()),
                    (float) (((cur.y-yAxisData.getMinimum())*yAxisData.getAxisScale())*animatedValue));
        }
        cubicPaint.setColor(curveData.getColor());
//        cubicPaint.setPathEffect(mPathEffect);
        canvas.drawPath(cubicPath,cubicPaint);
//        cubicFillPath.addPath(cubicPath);
        cubicPath.rewind();
    }
}
