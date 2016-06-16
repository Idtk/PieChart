package com.idtk.smallchart.data;

import android.graphics.drawable.Drawable;

import com.idtk.smallchart.interfaces.IData.ICurveData;

/**
 * Created by Idtk on 2016/6/7.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 曲线图数据类
 */
public class CurveData extends BarLineCurveData implements ICurveData {
    private float intensity = 0.2f;
    private Drawable drawable;

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public Drawable getDrawable() {
        return drawable;
    }
}
