package com.idtk.smallchart.data;

import com.idtk.smallchart.interfaces.IData.IBaseData;

/**
 * Created by Idtk on 2016/6/8.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 所有数据基类
 */
public class BaseData implements IBaseData {

    protected int color = android.graphics.Color.BLACK;

    protected int textSize = 30;
    /**
     * 是否显示图表的Y轴值
     */
    public boolean isTextSize = true;

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getTextSize() {
        return textSize;
    }
}
