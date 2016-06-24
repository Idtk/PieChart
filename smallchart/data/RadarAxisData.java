package com.idtk.smallchart.data;

import android.graphics.Color;

import com.idtk.smallchart.interfaces.iData.IRadarAxisData;

/**
 * Created by Idtk on 2016/6/21.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 雷达图坐标系类
 */
public class RadarAxisData extends AxisData implements IRadarAxisData {

    private String[] types;
    private int webColor = Color.GRAY;
    private float[] cosArray;
    private float[] sinArray;

    public void setTypes(String[] types) {
        this.types = types;
    }

    public String[] getTypes() {
        return types;
    }

    public void setWebColor(int webColor) {
        this.webColor = webColor;
    }

    public int getWebColor() {
        return webColor;
    }

    public float[] getCosArray() {
        return cosArray;
    }

    public void setCosArray(float[] cosArray) {
        this.cosArray = cosArray;
    }

    public float[] getSinArray() {
        return sinArray;
    }

    public void setSinArray(float[] sinArray) {
        this.sinArray = sinArray;
    }
}
