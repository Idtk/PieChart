package com.idtk.smallchart.data;

import android.graphics.Paint;

import com.idtk.smallchart.interfaces.IData.IPointData;

/**
 * Created by Idtk on 2016/6/8.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 */
public class PointData extends BaseData implements IPointData{
    private float outRadius;
    private float inRadius;
    private Paint outPaint;
    private Paint inPaint;


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

}
