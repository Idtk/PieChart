package com.idtk.smallchart.interfaces.IData;

/**
 * Created by Idtk on 2016/6/21.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 雷达图坐标系接口
 */
public interface IRadarAxisData extends IAxisData {

    void setTypes( String[] types);

    String[] getTypes();


    void setWebColor(int webColor);

    int getWebColor();

}
