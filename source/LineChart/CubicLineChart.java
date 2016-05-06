package com.example.administrator.customview.LineChart;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by DoBest on 2016/4/21.
 * author : Idtk
 */
public class CubicLineChart extends View {

    //Current View 长宽
    private int mWidth;
    private int mHeight;
    //传入的点数据
    private ArrayList<LineData> mLineDatas = new ArrayList<>();
    //动画
    private ValueAnimator animator = new ValueAnimator();
    private float animatedValue;
    private TimeInterpolator timeInterpolator = new DecelerateInterpolator();
    private long animatorDuration = 4000;
    //虚线
    private PathEffect mPathEffect = null;
    private Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.ITALIC);
    //触摸
    private boolean touchFlag = true;
    private int touchId = 0;
    private int touchArrayId = 0;
    //坐标系长宽及比例
    private float horizontal;
    private float vertical;
    private int timesX;
    private int timesY;
    private float[] coordinate = new float[6];
    //坐标系及点
    private Paint mPaint = new Paint();
    private Path mPath = new Path();
    private int mXYColor = Color.BLACK;
    //曲线
    private Path cubicPath = new Path();
    private Paint cubicPaint = new Paint();
    private float intensity = 0.2f;//Bezier 强度
    private int mCublicColor = Color.BLACK;
    //填充
    private Path cubicFillPath = new Path();
    private int drawId = Color.WHITE;

    /**
     * 设置画笔
     * @param context Setting Paint
     */
    public CubicLineChart(Context context) {
        super(context);
        mPaint.setStrokeWidth(1);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mXYColor);
        cubicPaint.setStrokeWidth(2);
        cubicPaint.setAntiAlias(true);
        cubicPaint.setColor(mCublicColor);
        cubicPaint.setStyle(Paint.Style.STROKE);
        cubicPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public CubicLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setStrokeWidth(1);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mXYColor);
        cubicPaint.setStrokeWidth(2);
        cubicPaint.setAntiAlias(true);
        cubicPaint.setColor(mCublicColor);
        cubicPaint.setStyle(Paint.Style.STROKE);
        cubicPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public CubicLineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint.setStrokeWidth(1);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mXYColor);
        cubicPaint.setStrokeWidth(2);
        cubicPaint.setAntiAlias(true);
        cubicPaint.setColor(mCublicColor);
        cubicPaint.setStyle(Paint.Style.STROKE);
        cubicPaint.setStrokeCap(Paint.Cap.ROUND);
    }


    /**
     * 获取ParentView传递的给当前View的width,height
     * @param w Current Width
     * @param h Current Height
     * @param oldw Old Width
     * @param oldh Old Height
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        touchId = mLineDatas.get(0).getValue().length;
        initAnimator(animatorDuration);
        horizontal = mWidth*3/5;
        vertical = mHeight*3/5;
    }

    /**
     *绘制
     * @param canvas the Canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        float x = mWidth*3/5;
//        float y = mHeight*3/5;
        canvas.translate(mWidth/5,mHeight*4/5);
        canvas.scale(1,-1);//翻转Y轴
        canvas.drawLine(0,0,horizontal,0,mPaint);
        canvas.drawLine(0,0,0,vertical,mPaint);
        //X轴
        timesX = (int) Math.floor((horizontal-horizontal/50)/(coordinate[1]-coordinate[0]));
        for (int i=0;(coordinate[2]*i+coordinate[0])<=coordinate[1];i++){
            canvas.drawLine(coordinate[2]*i*timesX,0, coordinate[2]*i*timesX,-vertical/50,mPaint);

            mPaint.setPathEffect(null);
//            mPaint.setAntiAlias(true);
            mPaint.setTextSize(20);
            mPaint.setAlpha(0xFF);
            mPaint.setTextAlign(Paint.Align.CENTER);
            mPaint.setTypeface(font);
            canvas.save();
            canvas.scale(1,-1);
            //设置小数点位数
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            numberFormat.setMaximumFractionDigits(0);
            //根据Paint的TextSize计算X轴的值
            float TextPathX = coordinate[2]*i*timesX;
            float TextPathY = (mPaint.descent()+mPaint.ascent())-vertical/50;
            canvas.drawText(numberFormat.format(coordinate[2]*i+coordinate[0]), TextPathX,-TextPathY,mPaint);
            canvas.restore();
        }
        canvas.drawLine(horizontal,0,horizontal-horizontal/50,horizontal/50,mPaint);
        canvas.drawLine(horizontal,0,horizontal-horizontal/50,-horizontal/50,mPaint);
        //Y轴
        timesY = (int) Math.floor((vertical-vertical/50)/(coordinate[4]-coordinate[3]));
        for (int i=0;(coordinate[5]*i+coordinate[3])<=coordinate[4];i++){
            //坐标轴刻度
            mPaint.setPathEffect(null);
            mPaint.setTextSize(20);
            mPaint.setAlpha(0xFF);
            mPaint.setTextAlign(Paint.Align.CENTER);
            mPaint.setTypeface(font);
            canvas.save();
            canvas.scale(1,-1);
            //设置小数点位数
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            numberFormat.setMaximumFractionDigits(0);
            //根据Paint的TextSize计算X轴的值
            float TextPathX = mPaint.measureText(coordinate[5]*i+coordinate[3]+"");
            float TextPathY = (mPaint.descent()+mPaint.ascent())/2+coordinate[5]*i*timesY;
            canvas.drawText(numberFormat.format(coordinate[5]*i+coordinate[3]),-TextPathX,-TextPathY,mPaint);
            canvas.restore();
        }
        mPath.rewind();//清除
        mPaint.setAntiAlias(true);
        canvas.drawLine(0,vertical,vertical/50,vertical-vertical/50,mPaint);
        canvas.drawLine(0,vertical,-vertical/50,vertical-vertical/50,mPaint);

        for (int i=0; i<mLineDatas.size(); i++){
            if (i==touchArrayId && touchId < mLineDatas.get(0).getValue().length){
                LineData mLineDataPoint = mLineDatas.get(i);
                mPaint.setTextSize(30);
                mPaint.setTextAlign(Paint.Align.CENTER);
                mPaint.setStrokeWidth(1);
                mPaint.setStyle(Paint.Style.FILL);
//                mPaint.setColor(Color.BLACK);
                Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();

                canvas.save();
                canvas.scale(1,-1);
                canvas.drawText("("+(mLineDataPoint.getValue()[touchId][0])+
                                ", "+mLineDataPoint.getValue()[touchId][1]+")",
                        (mLineDataPoint.getValue()[touchId][0]-coordinate[0])*timesX,
                        -(mLineDataPoint.getValue()[touchId][1]-coordinate[3])*timesY+(fontMetrics.top
                                +fontMetrics.bottom)/2,mPaint);
                canvas.restore();
            }
        }
        drawCubicBezier(canvas);
    }

    /**
     * Touch
     * @param event Touch Type
     * @return Point
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (touchFlag){
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    float x = event.getX()-(mWidth/5);
                    float y = (mHeight*4/5)-event.getY();
//                    Log.i("TAG",x+":"+y);
                    drawTouch(x,y);
                    invalidate();
                    return true;
                case MotionEvent.ACTION_UP:
                    touchId = mLineDatas.get(0).getValue().length;
                    invalidate();
                    return true;
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 动画
     * @param duration 动画时间
     */
    private void initAnimator(long duration){
        if (animator !=null &&animator.isRunning()){
            animator.cancel();
            animator.start();
        }else {
            animator= ValueAnimator.ofFloat(0,1).setDuration(duration);
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
     * 计算坐标系
     * @param mLineDatas 坐标集合
     */
    private void initCoordinate(ArrayList<LineData> mLineDatas){
        for (int i=0; i<mLineDatas.size();i++){
            LineData mLineData = mLineDatas.get(i);
            int length = mLineData.getValue().length;
            float maxX = Math.max(mLineData.getValue()[length-1][0],mLineData.getValue()[0][0]);
            float minX = Math.min(mLineData.getValue()[length-1][0],mLineData.getValue()[0][0]);
            float maxY = mLineData.getValue()[0][1];
            float minY = mLineData.getValue()[0][1];
            initAxis(1,mLineData,maxY,minY,i);
            initMaxMin(maxX,minX,false,i);

            /*for (int j=0; j<mLineData.getValue().length; j++){
                Log.i("TAG",(mLineData.getValue()[j][0])+":"+(mLineData.getValue()[j][1]));
            }*/
        }
        //默认所有的LineData。getValue()长度相同
        initScaling(coordinate[0],coordinate[1],mLineDatas.get(0).getValue().length,true);
        initScaling(coordinate[3],coordinate[4],mLineDatas.get(0).getValue().length,false);

//        Log.i("TAG", Arrays.toString(coordinate));
    }

    /**
     * * 计算Y轴数据
     * @param j Y轴
     * @param mLineData 单条曲线点数据
     * @param max 最大值
     * @param min 最小值
     * @param count 第几组数据
     */
    private void initAxis(int j, LineData mLineData,float max, float min, int count){
        for (int i=1; i<mLineData.getValue().length;i++){
            max = Math.max(mLineData.getValue()[i][j],max);
            min = Math.min(mLineData.getValue()[i][j],min);
        }
        initMaxMin(max,min,true,count);
    }

    /**
     * 计算最大最小整数值、区间量
     * @param max 最大值
     * @param min 最小值
     * @param flag X or Y 轴
     * @param count 第几组数据
     */
    private void initMaxMin(float max, float min, boolean flag, int count){
        max = (float) Math.ceil(max);
        min = (float) Math.floor(min);
        for (int i=1; i<6; i++){
            max += 1;
            if ((int)max%5 == 0)
                break;
        }
        for (int i=5; i>0; i--){
            min -= 1;
            if (min>=0&&(int)min%5 == 0)
                break;
            else if (min<0){
                min = 0;
                break;
            }
        }
        if (count == 0){
            if (!flag){
                coordinate[0] = min;
                coordinate[1] = max;
            }else {
                coordinate[3] = min;
                coordinate[4] = max;
            }
        }else {
            if (!flag){
                coordinate[0] = coordinate[0]> min?min:coordinate[0];
                coordinate[1] = coordinate[1]< max?max:coordinate[1];
            }else {
                coordinate[3] = coordinate[3]> min?min:coordinate[3];
                coordinate[4] = coordinate[4]< max?max:coordinate[4];
            }
        }
    }

    /**
     * 计算区间大小
     * @param min 最小值
     * @param max 最大值
     * @param length 数据长度
     * @param flag X or Y轴
     */
    private void initScaling(float min, float max,int length,boolean flag){
        float scaling = (float) 0.5;
        if ((max-min)>=(length-1)){
            scaling = 5;
        }
        if (!flag){
            coordinate[2] = scaling;
        }else {
            coordinate[5] = scaling;
        }
    }

    /**
     * 显示坐标
     * @param x 触摸点的当前坐标系的x值
     * @param y 触摸点的当前坐标系的y值
     */
    private void drawTouch(float x, float y){
        double minValue = horizontal;
        int minI =mLineDatas.size();
        int minJ =mLineDatas.get(0).getValue().length;
        for (int i=0; i< mLineDatas.size(); i++){
            for (int j=0; j< mLineDatas.get(i).getValue().length; j++){
                if (Math.abs((mLineDatas.get(i).getValue()[j][0]-coordinate[0])*timesX-x)<(horizontal/50)||
                        (Math.abs(mLineDatas.get(i).getValue()[j][1]-coordinate[3])*timesY-y)<(horizontal/50)){
                    /*double tanValue = Math.abs(((mLineDatas.get(i).getValue()[j][1]-coordinate[3])*timesY-y)
                            /((mLineDatas.get(i).getValue()[j][0]-coordinate[0])*timesX-x));
                    double radius = Math.abs(((mLineDatas.get(i).getValue()[j][0]-coordinate[0])*timesX-x)/
                            Math.cos(Math.toRadians(Math.atan(tanValue))));*/
                    float gapX = (mLineDatas.get(i).getValue()[j][0]-coordinate[0])*timesX-x;
                    float gapY = (mLineDatas.get(i).getValue()[j][1]-coordinate[3])*timesY-y;
                    double radius = Math.sqrt(gapX*gapX+gapY*gapY);
                    if (radius<minValue && radius<(horizontal/50)){
                        minI = i;
                        minJ = j;
                        minValue = radius;
                    }
                }
            }
        }
        touchArrayId = minI;
        touchId = minJ ;
    }

    /**
     * 绘制曲线
     * @param canvas the Canvas
     */
    private void drawCubicBezier(Canvas canvas){

        float prevDx = 0f;
        float prevDy = 0f;
        float curDx = 0f;
        float curDy = 0f;

        float[] prevPrev = mLineDatas.get(0).getValue()[0];
        float[] prev = prevPrev;
        float[] cur = prev;
        float[] next = mLineDatas.get(0).getValue()[1];

        for (int i=0; i<mLineDatas.size(); i++){
            LineData mLineData = mLineDatas.get(i);
            mPath.incReserve(mLineData.getValue().length);//为添加更多点准备路径,可以更有效地分配其存储的路径
            cubicPath.moveTo((mLineData.getValue()[0][0]-coordinate[0])*timesX,
                    ((mLineData.getValue()[0][1]-coordinate[3])*timesY)*animatedValue);
            for (int j=1; j< mLineData.getValue().length; j++){
                prevPrev = mLineDatas.get(i).getValue()[j == 1 ? 0 : j - 2];
                prev = mLineDatas.get(i).getValue()[j-1];
                cur = mLineDatas.get(i).getValue()[j];
                next = mLineData.getValue().length > j+1 ? mLineDatas.get(i).getValue()[j+1] : cur;

                prevDx = (cur[0]-prevPrev[0])*intensity*timesX;
                prevDy = (cur[1]-prevPrev[1])*intensity*timesY;
                curDx = (next[0]-prev[0])*intensity*timesX;
                curDy = (next[1]-prev[1])*intensity*timesY;

                cubicPath.cubicTo((prev[0]-coordinate[0])*timesX+prevDx,((prev[1]-coordinate[3])*timesY+prevDy)*animatedValue,
                        (cur[0]-coordinate[0])*timesX-curDx,((cur[1]-coordinate[3])*timesY-curDy)*animatedValue,
                        (cur[0]-coordinate[0])*timesX,((cur[1]-coordinate[3])*timesY)*animatedValue);

                mPaint.setStrokeWidth(1);
                mPaint.setStyle(Paint.Style.FILL);
//                mPaint.setColor(Color.BLACK);
                canvas.drawCircle((mLineData.getValue()[j][0]-coordinate[0])*timesX,
                        ((mLineData.getValue()[j][1]-coordinate[3])*timesY)*animatedValue,horizontal/70,mPaint);
            }

            canvas.drawCircle((mLineData.getValue()[0][0]-coordinate[0])*timesX,
                    ((mLineData.getValue()[0][1]-coordinate[3])*timesY)*animatedValue,horizontal/70,mPaint);

            cubicPaint.setColor(mLineData.getColor());
            cubicPaint.setPathEffect(mPathEffect);
            canvas.drawPath(cubicPath,cubicPaint);

            cubicFillPath.addPath(cubicPath);

            cubicPath.rewind();

            if (drawId != Color.WHITE){
                //填充颜色
                cubicFillPath.lineTo((mLineData.getValue()[mLineData.getValue().length-1][0]-coordinate[0])*timesX,0);
                cubicFillPath.lineTo((mLineData.getValue()[0][0]-coordinate[0])*timesX,0);
                cubicFillPath.close();

                canvas.save();
                canvas.clipPath(cubicFillPath);
                Drawable drawable = ContextCompat.getDrawable(getContext(), drawId);
                drawable.setBounds(0,0,(int)horizontal,(int) vertical);
                drawable.draw(canvas);
                canvas.restore();
                cubicFillPath.rewind();
            }
        }
    }

    /**
     * 设置数据集
     * @param lineDatas 数据集
     */
    public void setLineDatas(ArrayList<LineData> lineDatas) {
        mLineDatas = lineDatas;
        initCoordinate(mLineDatas);
    }

    /**
     * 刷新
     */
    public void setInvalidate(){
        invalidate();
    }

    /**
     * 是否可以点击
     * @param touchFlag 默认可点击
     */
    public void setTouchFlag(boolean touchFlag) {
        this.touchFlag = touchFlag;
    }

    /**
     * d动画时间
     * @param animatorDuration 默认4000
     */
    public void setAnimatorDuration(long animatorDuration) {
        this.animatorDuration = animatorDuration;
    }

    /**
     * 阴影颜色
     * @param drawId 默认白色
     */
    public void setDrawId(int drawId) {
        this.drawId = drawId;
    }

    /**
     * 曲线样式
     * @param pathEffect 默认实线
     */
    public void setPathEffect(PathEffect pathEffect) {
        mPathEffect = pathEffect;
    }

    /**
     * X、Y轴及点颜色
     * @param XYColor 默认黑色
     */
    public void setXYColor(int XYColor) {
        mXYColor = XYColor;
    }

    /**
     * 曲线颜色
     * @param cublicColor 默认黑色
     */
    public void setCublicColor(int cublicColor) {
        mCublicColor = cublicColor;
    }
}

