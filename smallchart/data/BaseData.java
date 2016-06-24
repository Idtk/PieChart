package com.idtk.smallchart.data;

import android.graphics.Color;

import com.idtk.smallchart.interfaces.iData.IBaseData;

/**
 * Created by Idtk on 2016/6/8.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 所有数据基类
 */
public class BaseData implements IBaseData {

    protected int color = Color.BLACK;
    protected float paintWidth = 1;
    protected float textSize = 30;
    /**
     * 是否显示图表的Y值
     */
    protected boolean isTextSize = true;

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setIsTextSize(boolean isTextSize){
        this.isTextSize = isTextSize;
    }

    public boolean getIsTextSize(){
        return isTextSize;
    }

    public void setPaintWidth(float paintWidth) {
        this.paintWidth = paintWidth;
    }

    public float getPaintWidth() {
        return paintWidth;
    }
}
