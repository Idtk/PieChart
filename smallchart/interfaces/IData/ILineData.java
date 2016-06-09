package com.idtk.smallchart.interfaces.IData;

import com.idtk.smallchart.data.LineData;

/**
 * Created by Administrator on 2016/6/7.
 */
public interface ILineData extends IBarLineCurveData {

    void setAnimatedMod(LineData.animatedMod animatedMod);

    LineData.animatedMod getAnimatedMod();
}
