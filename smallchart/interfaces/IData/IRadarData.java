package com.idtk.smallchart.interfaces.iData;

import java.util.ArrayList;

/**
 * Created by Idtk on 2016/6/20.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 雷达图数据类接口
 */
public interface IRadarData extends IChartData {
    /**
     * 设置雷达图值
     * @param value 数据集合
     */
    void setValue(ArrayList<Float> value);

    /**
     * 获取雷达图值
     * @return 数据集合
     */
    ArrayList<Float> getValue();

    /**
     * 设置雷达图透明度
     * @param alpha 透明度
     */
    void setAlpha(int alpha);

    /**
     * 获取雷达图透明度
     * @return 透明度
     */
    int getAlpha();
}
