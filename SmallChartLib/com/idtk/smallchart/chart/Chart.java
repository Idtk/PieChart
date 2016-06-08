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
 */
public abstract class Chart<T extends IChartData> extends View implements IChart{

    protected int mViewWidth,mViewHeight;
    protected int mWidth,mHeight;

    protected ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener;

    public Chart(Context context) {
        this(context,null);
    }

    public Chart(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Chart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mViewWidth = w;
        mViewHeight = h;

        mWidth = mViewWidth - getPaddingLeft() - getPaddingRight();
        mHeight = mViewHeight - getPaddingTop() - getPaddingBottom();
    }

    public abstract void setData(T chartData);

    protected abstract void init();
}
