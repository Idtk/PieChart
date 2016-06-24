package com.idtk.smallchart.data;

import android.graphics.Paint;

import com.idtk.smallchart.interfaces.iData.ILineData;
import com.idtk.smallchart.interfaces.iData.IPointData;

/**
 * Created by Idtk on 2016/6/7.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 折线图数据类
 */
public class LineData extends BarLineCurveData implements ILineData,IPointData {

    public float NOSETING=-1;
    private LineAnimated mLineAnimated = LineAnimated.SYNC;
    private float outRadius = NOSETING;
    private float inRadius = NOSETING;
    private Paint outPaint;
    private Paint inPaint;
    private PointShape pointShape = PointShape.CIRCLE;

    public void setAnimatedMod(LineAnimated LineAnimated) {
        mLineAnimated = LineAnimated;
    }

    public LineAnimated getLineAnimated() {
        return mLineAnimated;
    }

    public void setOutRadius(float outRadius) {
        this.outRadius = outRadius;
    }

    public float getOutRadius() {
        return outRadius;
    }

    public void setInRadius(float inRadius) {
        this.inRadius = inRadius;
    }

    public float getInRadius() {
        return inRadius;
    }

    public void setOutPaint(Paint outPaint) {
        this.outPaint = outPaint;
    }

    public Paint getOutPaint() {
        return outPaint;
    }

    public void setInPaint(Paint inPaint) {
        this.inPaint = inPaint;
    }

    public Paint getInPaint() {
        return inPaint;
    }

    @Override
    public void setPointShape(PointShape pointShape) {
        this.pointShape = pointShape;
    }

    @Override
    public PointShape getPointShape() {
        return pointShape;
    }
}
