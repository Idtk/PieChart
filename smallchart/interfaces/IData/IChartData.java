package com.idtk.smallchart.interfaces.iData;

/**
 * Created by Idtk on 2016/6/7.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 图表数据基类接口
 */
public interface IChartData extends IBaseData{

    /**
     * 设置图表名称
     * @param name 图表名
     */
    void setName(String name);

    /**
     * 获取图表名称
     * @return 图表名
     */
    String getName();

}
