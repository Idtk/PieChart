package com.idtk.smallchart.interfaces.IData;

import android.graphics.Paint;

import com.idtk.smallchart.data.PointData;

/**
 * Created by Idtk on 2016/6/8.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 */
public interface IPointData extends IBaseData {

    void setOutRadius(float outRadius);

    float getOutRadius();

    void setInRadius(float inRadius);

    float getInRadius();

    void setOutPaint(Paint outPaint);

    Paint getOutPaint();

    void setInPaint(Paint inPaint);

    Paint getInPaint();

    void setPointShape(PointData.PointShape pointShape);

    PointData.PointShape getPointShape();



}
