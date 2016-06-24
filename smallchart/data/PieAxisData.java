package com.idtk.smallchart.data;

import android.graphics.RectF;

import com.idtk.smallchart.interfaces.iData.IPieAxisData;

import java.util.Arrays;

/**
 * Created by Idtk on 2016/6/22.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 扇形图坐标系数据类
 * Center : name、textSize、color、width、isText
 * Round : in/out/offsetRadiusScale、startAngle、minAngle、decimal
 */
public class PieAxisData extends ChartData implements IPieAxisData{

    private float insideRadiusScale = 0.5f;
    private float outsideRadiusScale = 0.6f;
    private float offsetRadiusScale = 1.1f;
    private float startAngle = 0;
    private float minAngle = 30;
    private int decimalPlaces = 0;
    private float[] startAngles;
    private RectF[] rectFs;
    private RectF[] offsetRectFs;
    private float axisLength;

    public float getInsideRadiusScale() {
        return insideRadiusScale;
    }

    public void setInsideRadiusScale(float insideRadiusScale) {
        this.insideRadiusScale = insideRadiusScale;
    }

    public float getOutsideRadiusScale() {
        return outsideRadiusScale;
    }

    public void setOutsideRadiusScale(float outsideRadiusScale) {
        this.outsideRadiusScale = outsideRadiusScale;
    }

    public float getOffsetRadiusScale() {
        return offsetRadiusScale;
    }

    public void setOffsetRadiusScale(float offsetRadiusScale) {
        this.offsetRadiusScale = offsetRadiusScale;
    }

    @Override
    public float getStartAngle() {
        return startAngle;
    }

    @Override
    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
    }

    @Override
    public float getMinAngle() {
        return minAngle;
    }

    @Override
    public void setMinAngle(float minAngle) {
        this.minAngle = minAngle;
    }

    @Override
    public int getDecimalPlaces() {
        return decimalPlaces;
    }

    @Override
    public void setDecimalPlaces(int decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public float[] getStartAngles() {
        return startAngles;
    }

    public void setStartAngles(float[] startAngles) {
        this.startAngles = startAngles;
    }

    public RectF[] getRectFs() {
        return rectFs;
    }

    public void setRectFs(RectF[] rectFs) {
        this.rectFs = rectFs;
    }

    public RectF[] getOffsetRectFs() {
        return offsetRectFs;
    }

    public void setOffsetRectFs(RectF[] offsetRectFs) {
        this.offsetRectFs = offsetRectFs;
    }

    public float getAxisLength() {
        return axisLength;
    }

    public void setAxisLength(float axisLength) {
        this.axisLength = axisLength;
    }

    @Override
    public String toString() {
        return "PieAxisData{" +
                "insideRadiusScale=" + insideRadiusScale +
                ", outsideRadiusScale=" + outsideRadiusScale +
                ", offsetRadiusScale=" + offsetRadiusScale +
                ", startAngle=" + startAngle +
                ", minAngle=" + minAngle +
                ", decimalPlaces=" + decimalPlaces +
                ", startAngles=" + Arrays.toString(startAngles) +
                ", rectFs=" + Arrays.toString(rectFs) +
                ", offsetRectFs=" + Arrays.toString(offsetRectFs) +
                ", axisLength=" + axisLength +
                '}';
    }
}
