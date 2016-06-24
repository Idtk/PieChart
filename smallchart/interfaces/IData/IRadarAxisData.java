package com.idtk.smallchart.interfaces.iData;

/**
 * Created by Idtk on 2016/6/21.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 雷达图坐标系接口
 */
public interface IRadarAxisData extends IAxisData {
    /**
     * 设置雷达图各角字符串集合
     * @param types 字符串集合
     */
    void setTypes( String[] types);

    /**
     * 获取雷达图各角字符串集合
     * @return 字符串集合
     */
    String[] getTypes();

    /**
     * 设置雷达图坐标系颜色
     * @param webColor 颜色
     */
    void setWebColor(int webColor);

    /**
     * 获取雷达图坐标系颜色
     * @return 颜色
     */
    int getWebColor();

    /**
     * 获取雷达图cos数组
     * @return cos数组
     */
    float[] getCosArray();

    /**
     * 设置雷达图cos数组
     * @param cosArray cos数组
     */
    void setCosArray(float[] cosArray);

    /**
     * 获取雷达图sin数组
     * @return sin数组
     */
    float[] getSinArray();

    /**
     * 设置雷达图sin数组
     * @param sinArray sin数组
     */
    void setSinArray(float[] sinArray);
}
