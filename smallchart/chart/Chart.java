package com.idtk.smallchart.chart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.idtk.smallchart.interfaces.iChart.IChart;
import com.idtk.smallchart.interfaces.iData.IChartData;
import com.idtk.smallchart.render.ChartRender;

import java.util.ArrayList;

/**
 * Created by Idtk on 2016/6/6.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 图表绘制基类
 */
public abstract class Chart<T extends IChartData> extends View implements IChart{

    protected int mViewWidth,mViewHeight;
    protected int mWidth,mHeight;
    protected ArrayList<T> mDataList = new ArrayList<>();

    protected ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener;

    protected ArrayList<ChartRender> chartRenderList = new ArrayList<>();

    /**
     * 测量用画笔
     */
    protected Paint paintText = new Paint();

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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(
                Math.max(getSuggestedMinimumWidth(),
                        resolveSize(getCurrentWidth(),
                                widthMeasureSpec)),
                Math.max(getSuggestedMinimumHeight(),
                        resolveSize(getCurrentHeight(),
                                heightMeasureSpec)));
//        LogUtil.d("TAG",getCurrentWidth()+"______"+getCurrentHeight());
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

    /**
     * 设置图标数据
     * @param chartDataList 图表数据
     */
    public abstract void setDataList(ArrayList<T> chartDataList);

    public abstract int getCurrentWidth();

    public abstract int getCurrentHeight();
}
