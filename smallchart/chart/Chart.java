package com.idtk.smallchart.chart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.idtk.smallchart.interfaces.IChart.IChart;
import com.idtk.smallchart.interfaces.IData.IChartData;

/**
 * Created by Idtk on 2016/6/6.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 图表绘制基类
 */
public abstract class Chart<T extends IChartData> extends View implements IChart{

    protected int mViewWidth,mViewHeight;
    protected int mWidth,mHeight;

    protected ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener;

    public Chart(Context context) {
        super(context);
    }

    public Chart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Chart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mViewWidth = w;
        mViewHeight = h;

        mWidth = mViewWidth - getPaddingLeft() - getPaddingRight();
        mHeight = mViewHeight - getPaddingTop() - getPaddingBottom();
    }

    /**
     * 设置图表数据
     * @param chartData 图表数据
     */
    public abstract void setData(T chartData);
}
