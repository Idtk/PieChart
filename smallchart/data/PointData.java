package com.idtk.smallchart.data;

import android.graphics.Paint;

import com.idtk.smallchart.interfaces.IData.IPointData;

/**
 * Created by Idtk on 2016/6/8.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 图表点形状数据类
 */
public class PointData extends BaseData implements IPointData{

    /**
     * 图表点形状
     */
    public enum PointShape{
        CIRCLE,RECT,TRIANGLE
    }

    private float outRadius;
    private float inRadius;
    private Paint outPaint;
    private Paint inPaint;
    private PointShape pointShape;

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

    public void setPointShape(PointShape pointShape) {
        this.pointShape = pointShape;
    }

    public PointShape getPointShape() {
        return pointShape;
    }


}
