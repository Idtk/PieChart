package com.idtk.smallchart.interfaces.iData;

import android.graphics.drawable.Drawable;

/**
 * Created by Idtk on 2016/6/7.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 曲线图数据类接口
 */
public interface ICurveData extends IBarLineCurveData {

    /**
     * 设置曲线强度
     * @param intensity 强度
     */
    void setIntensity(float intensity);

    /**
     * 获取曲线强度
     * @return 强度
     */
    float getIntensity();

    void setDrawable(Drawable drawable);

    Drawable getDrawable();
}
