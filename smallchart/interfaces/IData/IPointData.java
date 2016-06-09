package com.idtk.smallchart.interfaces.IData;

import android.graphics.Paint;

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

}
