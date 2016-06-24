package com.idtk.smallchart.interfaces.iData;

import android.graphics.Paint;

import com.idtk.smallchart.data.PointShape;

/**
 * Created by Idtk on 2016/6/8.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 图表点形状数据类接口
 */
public interface IPointData extends IBaseData {

    float NOSETING=-1;
    /**
     * 设置图表点外半径
     * @param outRadius 外半径
     */
    void setOutRadius(float outRadius);

    /**
     * 获取图表点外半径
     * @return 外半径
     */
    float getOutRadius();

    /**
     * 设置图表点内半径
     * @param inRadius 内半径
     */
    void setInRadius(float inRadius);

    /**
     * 获取图表点内半径
     * @return 内半径
     */
    float getInRadius();

    /**
     * 设置图表点外部画笔
     * @param outPaint 画笔
     */
    void setOutPaint(Paint outPaint);

    /**
     * 获取图表点外部画笔
     * @return 画笔
     */
    Paint getOutPaint();

    /**
     * 设置图表点内部画笔
     * @param inPaint 画笔
     */
    void setInPaint(Paint inPaint);

    /**
     * 获取图表点内部画笔
     * @return 画笔
     */
    Paint getInPaint();

    /**
     * 设置图表点形状
     * @param pointShape 形状
     */
    void setPointShape(PointShape pointShape);

    /**
     * 获取图表点形状
     * @return 形状
     */
    PointShape getPointShape();



}
