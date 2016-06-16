package com.idtk.smallchart.interfaces.IData;

/**
 * Created by Idtk on 2016/6/8.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 所有数据类接口
 */
public interface IBaseData {
    /**
     * 设置图表颜色
     * @param color 颜色
     */
    void setColor(int color);

    /**
     * 获取图表颜色
     * @return 颜色
     */
    int getColor();

    /**
     * 设置图表文字大小
     * @param textSize 文字大小
     */
    void setTextSize(int textSize);

    /**
     * 获取图表文字大小
     * @return 文字大小
     */
    int getTextSize();
}
