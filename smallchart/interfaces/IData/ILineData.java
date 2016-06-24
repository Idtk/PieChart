package com.idtk.smallchart.interfaces.iData;

import com.idtk.smallchart.data.LineAnimated;

/**
 * Created by Idtk on 2016/6/7.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 折线图数据类接口
 */
public interface ILineData extends IBarLineCurveData{

    /**
     * 设置折线图动画模式
     * @param LineAnimated 动画模式
     */
    void setAnimatedMod(LineAnimated LineAnimated);

    /**
     * 获取折线图动画模式
     * @return 动画模式
     */
    LineAnimated getLineAnimated();
}
