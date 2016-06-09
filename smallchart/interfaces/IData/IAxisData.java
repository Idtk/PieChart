package com.idtk.smallchart.interfaces.IData;

/**
 * Created by Idtk on 2016/6/6.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 */
public interface IAxisData extends IBaseData{
    void setColor(int color);

    int getColor();

    void setTextSize(int textSize);

    int getTextSize();

    void setAxisLength(float axisLength);

    float getAxisLength();

    void setPaintWidth(float paintWidth);

    float getPaintWidth();

    void setMaximum(float maximum);

    float getMaximum();

    void setMinimum(float minimum);

    float getMinimum();

    void setInterval(float interval);

    float getInterval();

    void setUnit(String unit);

    String getUnit();

    void setDecimalPlaces(int decimalPlaces);

    int getDecimalPlaces();

    void setAxisScale(float axisScale);

    float getAxisScale();

    void setNarrowMax(float narrowMax);

    float getNarrowMax();

    void setNarrowMin(float narrowMin);

    float getNarrowMin();

}
