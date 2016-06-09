package com.idtk.smallchart.data;

import com.idtk.smallchart.interfaces.IData.ILineData;

/**
 * Created by Administrator on 2016/6/7.
 */
public class LineData extends BarLineCurveData implements ILineData {

    public enum animatedMod{
        SYNC,ASYNC

    }

    private animatedMod mAnimatedMod = animatedMod.SYNC;

    public void setAnimatedMod(animatedMod animatedMod) {
        mAnimatedMod = animatedMod;
    }

    public animatedMod getAnimatedMod() {
        return mAnimatedMod;
    }
}
