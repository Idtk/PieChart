package com.idtk.smallchart.data;

import com.idtk.smallchart.interfaces.iData.IRadarData;

import java.util.ArrayList;

/**
 * Created by Idtk on 2016/6/20.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 雷达图数据类
 */
public class RadarData extends ChartData implements IRadarData {
    private ArrayList<Float> value;
    private int alpha = 0x80;

    public void setValue(ArrayList<Float> value) {
        this.value = value;
    }

    public ArrayList<Float> getValue() {
        return value;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int getAlpha() {
        return alpha;
    }

}
