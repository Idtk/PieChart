package com.idtk.smallchart.chart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.idtk.smallchart.animation.ChartAnimator;
import com.idtk.smallchart.compute.ComputePie;
import com.idtk.smallchart.data.PieAxisData;
import com.idtk.smallchart.interfaces.iChart.IPieChart;
import com.idtk.smallchart.interfaces.iData.IPieAxisData;
import com.idtk.smallchart.interfaces.iData.IPieData;
import com.idtk.smallchart.render.ChartRender;
import com.idtk.smallchart.render.PieChartRender;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Idtk on 2016/6/22.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 扇形图绘制类
 * isAnimated、isTouch
 * 子Pie集合操作 : widths、textSizes、
 */
public class PieChart extends PieRadarChart<IPieData> implements IPieChart{

    private IPieAxisData mPieAxisData = new PieAxisData();
    private ComputePie mComputePie = new ComputePie(mPieAxisData);
//    private float radius;

    private RectF rectF=new RectF(),rectFOut = new RectF(),rectFIn = new RectF();
    private float radius,radiusOut,radiusIn;
    private RectF offsetRectF = new RectF(),offsetRectFOut = new RectF(),offsetRectFIn = new RectF();
    private float offsetRadius,offsetRadiusOut,offsetRadiusIn;
    private RectF[] mRectFs = new RectF[3];
    private RectF[] mOffsetRectFs = new RectF[3];
    private float animatedValue;
    private ChartAnimator mChartAnimator;
    private PieChartRender mPieChartRender;
    public boolean isAnimated = true,isTouch = true;

    private int angleId = -1;
    private Paint mPaint = new Paint();
    //引入Path
    private Path outPath = new Path();
    private Path midPath = new Path();
    private Path inPath = new Path();
    private Path outMidPath = new Path();
    private Path midInPath = new Path();

    public PieChart(Context context) {
        super(context);
    }

    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PieChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        radius = Math.min(mWidth,mHeight)*0.4f;

        if (radius*2>Math.min(mWidth,mHeight)){
            radius=0;
        }

        mPieAxisData.setAxisLength(radius);

        /**
         * 设置圆环图的半径，透明内外半径，以及点击偏移时的半径
         */
        // 饼状图绘制区域
        rectF.left = -radius;
        rectF.top = -radius;
        rectF.right =radius;
        rectF.bottom = radius;
        mRectFs[0] = rectF;
        //白色圆弧
        //透明圆弧
        radiusOut = (float) (radius*mPieAxisData.getOutsideRadiusScale());
        rectFOut.left = -radiusOut;
        rectFOut.top = -radiusOut;
        rectFOut.right = radiusOut;
        rectFOut.bottom = radiusOut;
        mRectFs[1] = rectFOut;
        //白色圆
        radiusIn = (float) (radius*mPieAxisData.getInsideRadiusScale());
        rectFIn.left = -radiusIn;
        rectFIn.top = -radiusIn;
        rectFIn.right = radiusIn;
        rectFIn.bottom = radiusIn;
        mRectFs[2] = rectFIn;

        //浮出圆环
        //圆弧
        offsetRadius = (float) (radius*mPieAxisData.getOffsetRadiusScale());// 饼状图半径
        // 饼状图绘制区域
        offsetRectF.left = -offsetRadius;
        offsetRectF.top = -offsetRadius;
        offsetRectF.right = offsetRadius;
        offsetRectF.bottom = offsetRadius;
        mOffsetRectFs[0] = offsetRectF;
        //白色圆弧
        //透明圆弧
        offsetRadiusOut = (float) (offsetRadius*mPieAxisData.getOutsideRadiusScale());
        offsetRectFOut.left = -offsetRadiusOut;
        offsetRectFOut.top = -offsetRadiusOut;
        offsetRectFOut.right = offsetRadiusOut;
        offsetRectFOut.bottom = offsetRadiusOut;
        mOffsetRectFs[1] = offsetRectFOut;
        //白色扇形
        offsetRadiusIn = (float) (offsetRadius*mPieAxisData.getInsideRadiusScale());
        offsetRectFIn.left = -offsetRadiusIn;
        offsetRectFIn.top = -offsetRadiusIn;
        offsetRectFIn.right = offsetRadiusIn;
        offsetRectFIn.bottom = offsetRadiusIn;
        mOffsetRectFs[2] = offsetRectFIn;
        /**
         * 设置扇形半径、透明内外半径属性
         */
        mPieAxisData.setRectFs(mRectFs);
        mPieAxisData.setOffsetRectFs(mOffsetRectFs);

        animated();

        chartRenderList.clear();
        for (int i=0; i<mDataList.size(); i++){
            mPieChartRender = new PieChartRender(mPieAxisData,mDataList.get(i));
            chartRenderList.add(mPieChartRender);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.translate(mViewWidth/2,mViewHeight/2);// 将画布坐标原点移动到中心位置
        /*if (mDataList == null)
            return;*/
        canvas.save();
        canvas.rotate(mPieAxisData.getStartAngle());
        for (ChartRender chartRender : chartRenderList){
            chartRender.drawGraph(canvas,animatedValue);
        }
        canvas.restore();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isTouch&&mDataList.size()>0){
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    float x = event.getX()-(mWidth/2);
                    float y = event.getY()-(mHeight/2);
                    float touchAngle = 0;
                    if (x<0&&y<0){
                        touchAngle += 180;
                    }else if (y<0&&x>0){
                        touchAngle += 360;
                    }else if (y>0&&x<0){
                        touchAngle += 180;
                    }
                    touchAngle +=Math.toDegrees(Math.atan(y/x));
                    touchAngle = touchAngle-mPieAxisData.getStartAngle();
                    if (touchAngle<0){
                        touchAngle = touchAngle+360;
                    }
                    float touchRadius = (float) Math.sqrt(y*y+x*x);
                    if (radiusOut< touchRadius && touchRadius< radius){
                        angleId = -Arrays.binarySearch(mPieAxisData.getStartAngles(),(touchAngle))-1;
                        PieChartRender pieChartRender = (PieChartRender)chartRenderList.get(angleId);
                        pieChartRender.setTouchFlag(true);
                        invalidate();
                    }
                    return true;
                case MotionEvent.ACTION_UP:
//                    angleId = -1;
                    PieChartRender pieChartRender = (PieChartRender)chartRenderList.get(angleId);
                    pieChartRender.setTouchFlag(false);
                    invalidate();
                    return true;
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 动画方法，默认动画时间两秒，生成监听值animatedValue
     * 如果不使用动画，则直接设置监听值为1
     */
    protected void animated() {
        if (!isAnimated) {
            animatedValue = 360f;
        } else {
            mAnimatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    animatedValue = (float) animation.getAnimatedValue();
                    invalidate();
                }
            };
            mChartAnimator = new ChartAnimator(mAnimatorUpdateListener);
            mChartAnimator.animatedY(2000,360f);
        }
    }

    @Override
    public void setData(IPieData chartData) {
        super.setData(chartData);
        computePie();
    }

    @Override
    public void setDataList(ArrayList<IPieData> chartDataList) {
        super.setDataList(chartDataList);
        computePie();
    }

    @Override
    public int getCurrentWidth() {
        int wrapSize;
        if (mDataList!=null&&mDataList.size()>1){
            NumberFormat numberFormat =NumberFormat.getPercentInstance();
            numberFormat.setMinimumFractionDigits(mPieAxisData.getDecimalPlaces());
            paintText.setTextSize(mPieAxisData.getTextSize());
            paintText.setStrokeWidth(mPieAxisData.getPaintWidth());
            float percentWidth = paintText.measureText(numberFormat.format(10));
            float nameWidth = paintText.measureText(mPieAxisData.getName());
            wrapSize = (int) ((percentWidth*4+nameWidth*1.1)* mPieAxisData.getOffsetRadiusScale());
        }else {
            wrapSize = 0;
        }
        return wrapSize;
    }
    @Override
    public int getCurrentHeight() {
        int wrapSize;
        if (mDataList!=null&&mDataList.size()>1){
            NumberFormat numberFormat =NumberFormat.getPercentInstance();
            numberFormat.setMinimumFractionDigits(mPieAxisData.getDecimalPlaces());
            paintText.setTextSize(mPieAxisData.getTextSize());
            paintText.setStrokeWidth(mPieAxisData.getPaintWidth());
            float percentWidth = paintText.measureText(numberFormat.format(10));
            float nameWidth = paintText.measureText(mPieAxisData.getName());
            wrapSize = (int) ((percentWidth*4+nameWidth*1.1)* mPieAxisData.getOffsetRadiusScale());
        }else {
            wrapSize = 0;
        }
        return wrapSize;
    }

    @Override
    public void computePie() {
        mComputePie.computePie(mDataList);
    }

    @Override
    public void setAxisTextSize(float axisTextSize) {
        mPieAxisData.setTextSize(axisTextSize);
    }

    @Override
    public void setAxisColor(int axisColor) {
        mPieAxisData.setColor(axisColor);
    }

    @Override
    public void setAxisWidth(float axisWidth) {
        mPieAxisData.setPaintWidth(axisWidth);
    }

    @Override
    public void setInsideRadiusScale(float insideRadiusScale) {
        mPieAxisData.setInsideRadiusScale(insideRadiusScale);
    }

    @Override
    public void setOutsideRadiusScale(float outsideRadiusScale) {
        mPieAxisData.setOutsideRadiusScale(outsideRadiusScale);
    }

    @Override
    public void setOffsetRadiusScale(float offsetRadiusScale) {
        mPieAxisData.setOffsetRadiusScale(offsetRadiusScale);
    }

    @Override
    public void setStartAngle(float startAngle) {
        while (startAngle<0){
            startAngle = startAngle+360;
        }
        while (startAngle>360){
            startAngle = startAngle-360;
        }
        mPieAxisData.setStartAngle(startAngle);
    }

    @Override
    public void setMinAngle(float minAngle) {
        mPieAxisData.setMinAngle(minAngle);
    }

    @Override
    public void setDecimalPlaces(int decimalPlaces) {
        mPieAxisData.setDecimalPlaces(decimalPlaces);
    }

}
