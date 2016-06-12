package com.idtk.smallchart.data;

import com.idtk.smallchart.interfaces.IData.ICurveData;

/**
 * Created by Administrator on 2016/6/7.
 */
public class CurveData extends BarLineCurveData implements ICurveData {
    private float intensity;

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public float getIntensity() {
        return intensity;
    }
}
