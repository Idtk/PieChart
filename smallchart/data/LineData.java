package com.idtk.smallchart.data;

import com.idtk.smallchart.interfaces.IData.ILineData;

/**
 * Created by Administrator on 2016/6/7.
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
