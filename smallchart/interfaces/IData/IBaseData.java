package com.idtk.smallchart.interfaces.iData;

/**
 * Created by Idtk on 2016/6/8.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 所有数据类接口
 */
public interface IBaseData {

    /**
     * 是否显示Y值
     * @param isTextSize boolean
     */
    void setIsTextSize(boolean isTextSize);

    /**
     * 是否显示Y值
     * @return boolean
     */
    boolean getIsTextSize();

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
    void setTextSize(float textSize);

    /**
     * 获取图表文字大小
     * @return 文字大小
     */
    float getTextSize();

    /**
     * 设置画笔宽度
     * @param paintWidth 画笔宽度
     */
    void setPaintWidth(float paintWidth);

    /**
     * 获取画笔宽度
     * @return 画笔宽度
     */
    float getPaintWidth();
}
