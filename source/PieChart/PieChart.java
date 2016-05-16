package com.example.administrator.customview.PieChart;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by DoBest on 2016/4/15.
 * author : Idtk
 */
public class PieChart extends View {

    //画笔
    private Paint mPaint = new Paint();
    //宽高
    private int mWidth;
    private int mHeight;
    //数据
    private ArrayList<PieData> mPieData = new ArrayList<>();
    //饼状图初始绘制角度
    private float mStartAngle = 0;
    private RectF rectF,rectFTra;
    private float r,rTra,rWhite;
    private RectF rectFF,rectFTraF,reatFWhite;
    private float rF,rTraF;
    //动画
    private ValueAnimator animator;
    private float animatedValue;
    private long animatorDuration = 5000;
    private TimeInterpolator timeInterpolator = new AccelerateDecelerateInterpolator();
    //Touch
    private boolean touchFlag = true;
    private float[] pieAngles;
    private int angleId;
    private double offsetScaleRadius = 1.1;
    //圆环半径比例
    private double widthScaleRadius = 0.8;
    private double radiusScaleTransparent = 0.5;
    private double radiusScaleInside = 0.43;
    //Paint的字体大小
    private int percentTextSize = 60;
    private int centerTextSize = 80;
    //中间园的颜色
    private int centerColor = Color.WHITE;
    //中间文字颜色
    private int centerTextColor = Color.BLACK;
    //百分比文字颜色
    private int percentTextColor = Color.WHITE;
    //百分比的小数位
    private int percentDecimal = 0;

    public PieChart(Context context) {
        super(context);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);//抗锯齿
        initAnimator(animatorDuration);
    }

    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        initAnimator(animatorDuration);
    }

    public PieChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        initAnimator(animatorDuration);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        //标准圆环
        //圆弧
        r = (float) (Math.min(mWidth,mHeight)/2*widthScaleRadius);// 饼状图半径
        rectF = new RectF(-r,-r,r,r);// 饼状图绘制区域
        //白色圆弧
        //透明圆弧
        rTra = (float) (r*radiusScaleTransparent);
        rectFTra = new RectF(-rTra,-rTra,rTra,rTra);
        //白色圆
        rWhite = (float) (r*radiusScaleInside);

        //浮出圆环
        //圆弧
        rF = (float) (Math.min(mWidth,mHeight)/2*widthScaleRadius*offsetScaleRadius);// 饼状图半径
        rectFF = new RectF(-rF,-rF,rF,rF);// 饼状图绘制区域
        //白色圆弧
        //透明圆弧
        rTraF = (float) (rF*radiusScaleTransparent);
        rectFTraF = new RectF(-rTraF,-rTraF,rTraF,rTraF);
        //白色扇形
        float rWhiteF = (float) (rF*radiusScaleInside);
        reatFWhite = new RectF(-rWhiteF,-rWhiteF,rWhiteF,rWhiteF);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPieData == null)
            return;
        float currentStartAngle = 0;// 当前起始角度
        canvas.translate(mWidth/2,mHeight/2);// 将画布坐标原点移动到中心位置

        canvas.save();
        canvas.rotate(mStartAngle);
        for (int i=0; i<mPieData.size(); i++){
            PieData pie = mPieData.get(i);
            float drawAngle;
            if (Math.min(pie.getAngle()-1,animatedValue-currentStartAngle)>=0){
                drawAngle = Math.min(pie.getAngle()-1,animatedValue-currentStartAngle);
            }else {
                drawAngle = 0;
            }
            if (i==angleId){
                //圆弧
                mPaint.setColor(pie.getColor());
                canvas.drawArc(rectFF,currentStartAngle,drawAngle,true,mPaint);
                //白色圆弧
                mPaint.setColor(Color.WHITE);
                canvas.drawArc(rectFTraF,currentStartAngle,drawAngle,true,mPaint);
                //透明圆弧
                mPaint.setColor(pie.getColor());
                mPaint.setAlpha(0x80);//设置透明度
                canvas.drawArc(rectFTraF,currentStartAngle,drawAngle,true,mPaint);
                //白色扇形
                mPaint.setColor(Color.WHITE);
                canvas.drawArc(reatFWhite,currentStartAngle,drawAngle,true,mPaint);
            }else {
                //圆弧
                mPaint.setColor(pie.getColor());
                canvas.drawArc(rectF,currentStartAngle,drawAngle,true,mPaint);
                //白色圆弧
                mPaint.setColor(Color.WHITE);
                canvas.drawArc(rectFTra,currentStartAngle,drawAngle,true,mPaint);
                //透明圆弧
                mPaint.setColor(pie.getColor());
                mPaint.setAlpha(0x80);//设置透明度
                canvas.drawArc(rectFTra,currentStartAngle,drawAngle,true,mPaint);
            }
            currentStartAngle += pie.getAngle();
        }
        canvas.restore();
        currentStartAngle = mStartAngle;
        //扇形百分比文字
        for (int i=0; i<mPieData.size(); i++){
            PieData pie = mPieData.get(i);
            mPaint.setColor(percentTextColor);
            mPaint.setTextSize(percentTextSize);
            mPaint.setTextAlign(Paint.Align.CENTER);

            NumberFormat numberFormat =NumberFormat.getPercentInstance();
            numberFormat.setMinimumFractionDigits(percentDecimal);
            //根据Paint的TextSize计算Y轴的值
            Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
            int textPathX;
            int textPathY;
            if (i==angleId){
                textPathX = (int) (Math.cos(Math.toRadians(currentStartAngle+(pie.getAngle()/2)))*(rF+rTraF)/2);
                textPathY = (int) (Math.sin(Math.toRadians(currentStartAngle+(pie.getAngle()/2)))*(rF+rTraF)/2);
                canvas.drawText(pie.getName(),textPathX,textPathY,mPaint);
                canvas.drawText(numberFormat.format(pie.getPercentage()),textPathX,textPathY+
                        (fontMetrics.bottom+fontMetrics.ascent)/-2-(fontMetrics.bottom+fontMetrics.ascent),mPaint);
            }else {
                textPathX = (int) (Math.cos(Math.toRadians(currentStartAngle+(pie.getAngle()/2)))*(r+rTra)/2);
                textPathY = (int) (Math.sin(Math.toRadians(currentStartAngle+(pie.getAngle()/2)))*(r+rTra)/2);
                canvas.drawText(numberFormat.format(pie.getPercentage()),textPathX,textPathY+
                        (fontMetrics.bottom+fontMetrics.ascent)/-2,mPaint);
            }
            currentStartAngle += pie.getAngle();
        }
        //白色圆
        mPaint.setColor(centerColor);
        canvas.drawCircle(0,0,rWhite,mPaint);
        //饼图名
        mPaint.setColor(centerTextColor);
        mPaint.setTextSize(centerTextSize);
        mPaint.setTextAlign(Paint.Align.CENTER);
        //根据Paint的TextSize计算Y轴的值
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
//        Log.i("TAG",fontMetrics.bottom+":"+fontMetrics.top+":"+fontMetrics.ascent+":"+fontMetrics.descent+":"+fontMetrics.leading);
        canvas.drawText("饼图名",0,(fontMetrics.bottom+fontMetrics.ascent)/-2,mPaint);
//        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (touchFlag){
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
                    /*while (touchAngle>360){
                        touchAngle -= 360;
                    }*/
//                    touchAngle = touchAngle+mStartAngle;
                    /*if(touchAngle<360){
                        touchAngle = touchAngle-mStartAngle;
                    }else {
                        touchAngle = touchAngle+mStartAngle-360;
                    }*/
                    touchAngle = touchAngle-mStartAngle;
                    if (touchAngle<0){
                        touchAngle = touchAngle+360;
                    }
                    Log.i("Search",Arrays.binarySearch(pieAngles,(touchAngle))+":"+pieAngles.length+":"+touchAngle);
                    float touchRadius = (float) Math.sqrt(y*y+x*x);
                    if (rTra< touchRadius && touchRadius< r){
//                        Log.i("touch",-Arrays.binarySearch(pieAngles,touchAngle)+"");
                        angleId = -Arrays.binarySearch(pieAngles,(touchAngle))-1;
                        invalidate();
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                    angleId = mPieData.size();
                    invalidate();
                    return true;
            }
        }
//        Log.d("TAG",event.getAction()+":"+MotionEvent.ACTION_DOWN+":"+MotionEvent.ACTION_UP);
        return super.onTouchEvent(event);
    }

    private void initDate(ArrayList<PieData> mPieData){
        if (mPieData ==null||mPieData.size()==0)
            return;
        pieAngles = new float[mPieData.size()];
        float sumValue = 0;
        for (int i=0; i<mPieData.size(); i++){
            PieData pie = mPieData.get(i);
            sumValue += pie.getValue();
        }

        float sumAngle = 0;
        for (int i=0; i<mPieData.size();i++){
            PieData pie = mPieData.get(i);
            float percentage = pie.getValue()/sumValue;
            float angle = percentage*360;
            pie.setPercentage(percentage);
            pie.setAngle(angle);
            sumAngle += angle;
            /*while (sumAngle>360){
                sumAngle -= 360;
            }*/
            pieAngles[i]=sumAngle;
        }
        Log.i("Search", Arrays.toString(pieAngles));
        angleId =mPieData.size();
    }

    private void initAnimator(long duration){
        if (animator !=null &&animator.isRunning()){
            animator.cancel();
            animator.start();
        }else {
            animator=ValueAnimator.ofFloat(0,360).setDuration(duration);
            animator.setInterpolator(timeInterpolator);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    animatedValue = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            animator.start();
        }
    }

    /**
     * 设置起始角度
     * @param mStartAngle 起始角度
     */
    public void setStartAngle(float mStartAngle) {
        while (mStartAngle<0){
            mStartAngle = mStartAngle+360;
        }
        while (mStartAngle>360){
            mStartAngle = mStartAngle-360;
        }
        this.mStartAngle = mStartAngle;
    }

    /**
     * 设置数据
     * @param mPieData 数据
     */
    public void setPieData(ArrayList<PieData> mPieData) {
        this.mPieData = mPieData;
        initDate(mPieData);
    }

    /**
     * 重绘View
     */
    public void setInvalidate(){
        invalidate();
    }

    /**
     * 是否显示点触效果
     * @param touchFlag 是否显示点触效果
     */
    public void setTouchFlag(boolean touchFlag) {
        this.touchFlag = touchFlag;
    }

    /**
     * 设置绘制圆环的动画时间
     * @param animatorDuration 动画时间
     */
    public void setAnimatorDuration(long animatorDuration) {
        this.animatorDuration = animatorDuration;
    }

    /**
     * 设置偏移扇形与原扇形的半径比例
     * @param offsetScaleRadius 点触扇形的偏移比例
     */
    public void setOffsetScaleRadius(double offsetScaleRadius) {
        this.offsetScaleRadius = offsetScaleRadius;
    }

    /**
     * 设置圆环外层园的半径与视图的宽度比
     * @param widthScaleRadius 外圆环半径与视图宽度比
     */
    public void setWidthScaleRadius(double widthScaleRadius) {
        this.widthScaleRadius = widthScaleRadius;
    }

    /**
     * 设置透明圆环与外圆环半径比
     * @param radiusScaleTransparent 透明圆环与外圆环半径比
     */
    public void setRadiusScaleTransparent(double radiusScaleTransparent) {
        this.radiusScaleTransparent = radiusScaleTransparent;
    }

    /**
     * 设置内部圆与外部圆环半径比
     * @param radiusScaleInside 内部圆与外部圆环半径比
     */
    public void setRadiusScaleInside(double radiusScaleInside) {
        this.radiusScaleInside = radiusScaleInside;
    }

    /**
     * 设置圆环显示的百分比画笔大小
     * @param percentTextSize 百分比画笔大小
     */
    public void setPercentTextSize(int percentTextSize) {
        this.percentTextSize = percentTextSize;
    }

    /**
     * 设置圆环显示的百分比字体颜色
     * @param percentTextColor 百分比字体颜色
     */
    public void setPercentTextColor(int percentTextColor) {
        this.percentTextColor = percentTextColor;
    }

    /**
     * 设置中心圆颜色
     * @param centerColor 中心圆颜色
     */
    public void setCenterColor(int centerColor) {
        this.centerColor = centerColor;
    }

    /**
     * 设置中心文字画笔大小
     * @param centerTextSize 中心文字画笔大小
     */
    public void setCenterTextSize(int centerTextSize) {
        this.centerTextSize = centerTextSize;
    }

    /**
     * 设置中心文字颜色
     * @param centerTextColor 中心文字颜色
     */
    public void setCenterTextColor(int centerTextColor) {
        this.centerTextColor = centerTextColor;
    }

    /**
     * 设置动画类型
     * @param timeInterpolator 动画类型
     */
    public void setTimeInterpolator(TimeInterpolator timeInterpolator) {
        this.timeInterpolator = timeInterpolator;
    }

    /**
     * 设置百分比的小数位
     * @param percentDecimal 百分比的小数位
     */
    public void setPercentDecimal(int percentDecimal) {
        this.percentDecimal = percentDecimal;
    }
}
