package com.idtk.smallchart.data;

import com.idtk.smallchart.interfaces.IData.IBaseData;

/**
 * Created by Idtk on 2016/6/8.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 */
public class BaseData implements IBaseData {

    protected int color = android.graphics.Color.BLACK;

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}
