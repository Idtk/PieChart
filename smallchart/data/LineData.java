package com.idtk.smallchart.data;

import com.idtk.smallchart.interfaces.IData.ILineData;

/**
 * Created by Idtk on 2016/6/7.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 折线图数据类
 */
public class LineData extends BarLineCurveData implements ILineData {

    public enum AnimatedMod{
        SYNC,ASYNC
    }

    private AnimatedMod mAnimatedMod = AnimatedMod.SYNC;

    public void setAnimatedMod(AnimatedMod animatedMod) {
        mAnimatedMod = animatedMod;
    }

    public AnimatedMod getAnimatedMod() {
        return mAnimatedMod;
    }
}
