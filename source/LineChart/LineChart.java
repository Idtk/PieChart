package com.example.administrator.customview.LineChart;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by DoBest on 2016/4/21.
 * author : Idtk
 */
public class LineChart extends View {

    private int mWidth;
    private int mHeight;
    private ArrayList<LineData> mLineDatas = new ArrayList<>();
    private float[] coordinate = new float[6];
    private Paint mPaint = new Paint();
    private Path mPath = new Path();
    //动画
    private ValueAnimator animator = new ValueAnimator();
    private float animatedValue;
    private TimeInterpolator timeInterpolator = new DecelerateInterpolator();
    private long animatorDuration = 10000;
    private PathEffect mPathEffect = new DashPathEffect(new float[]{20,10},1);
    private Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.ITALIC);
    //是否同时到达
    private int flag = 0;

    public LineChart(Context context) {
        super(context);
        mPaint.setStrokeWidth(1);
        mPaint.setAntiAlias(true);
    }

    public LineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setStrokeWidth(1);
        mPaint.setAntiAlias(true);
    }

    public LineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint.setStrokeWidth(1);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        initAnimator(animatorDuration,mHeight*3/5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.BLACK);
        float x = mWidth*3/5;
        float y = mHeight*3/5;
        canvas.translate(mWidth/5,mHeight*4/5);
        canvas.scale(1,-1);//翻转Y轴
        canvas.drawLine(0,0,x,0,mPaint);
        canvas.drawLine(0,0,0,y,mPaint);
        //X轴
        int timesX = (int) Math.floor((x-x/50)/(coordinate[1]-coordinate[0]));
        for (int i=0;(coordinate[2]*i+coordinate[0])<=coordinate[1];i++){
            canvas.drawLine(coordinate[2]*i*timesX,0, coordinate[2]*i*timesX,-y/50,mPaint);

            mPaint.setPathEffect(null);
            mPaint.setAntiAlias(true);
            mPaint.setTextSize(30);
            mPaint.setAlpha(0xFF);
            mPaint.setTextAlign(Paint.Align.CENTER);
            mPaint.setTypeface(font);
            canvas.scale(1,-1);
            //设置小数点位数
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            numberFormat.setMaximumFractionDigits(0);
            //根据Paint的TextSize计算X轴的值
            float TextPathX = coordinate[2]*i*timesX;
            float TextPathY = (mPaint.descent()+mPaint.ascent())-y/50;
            canvas.drawText(numberFormat.format(coordinate[2]*i+coordinate[0]), TextPathX,-TextPathY,mPaint);
            canvas.scale(1,-1);
        }
        canvas.drawLine(x,0,x-x/50,x/50,mPaint);
        canvas.drawLine(x,0,x-x/50,-x/50,mPaint);
        //Y轴
        int timesY = (int) Math.floor((y-y/50)/(coordinate[4]-coordinate[3]));
        for (int i=0;(coordinate[5]*i+coordinate[3])<=coordinate[4];i++){
//            canvas.drawLine(0,coordinate[5]*i*timesY, x,coordinate[5]*i*timesY,mPaint);
            mPaint.setPathEffect(mPathEffect);
            mPaint.setAlpha(0x80);
            mPath.moveTo(0,coordinate[5]*i*timesY);
            mPath.lineTo(x,coordinate[5]*i*timesY);
            canvas.drawPath(mPath,mPaint);
            //坐标轴刻度
//            mPaint.reset();
            mPaint.setPathEffect(null);
            mPaint.setAntiAlias(true);
            mPaint.setTextSize(30);
            mPaint.setAlpha(0xFF);
            mPaint.setTextAlign(Paint.Align.CENTER);
            mPaint.setTypeface(font);
            canvas.scale(1,-1);
            //设置小数点位数
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            numberFormat.setMaximumFractionDigits(0);
            //根据Paint的TextSize计算X轴的值
            float TextPathX = mPaint.measureText(coordinate[5]*i+coordinate[3]+"");
            float TextPathY = (mPaint.descent()+mPaint.ascent())/2+coordinate[5]*i*timesY;
            canvas.drawText(numberFormat.format(coordinate[5]*i+coordinate[3]),-TextPathX,-TextPathY,mPaint);
            canvas.scale(1,-1);
//            Log.i("TAG",coordinate[5]*i+coordinate[3]+"");
        }
        mPaint.reset();
        mPath.rewind();//清除
        mPaint.setAntiAlias(true);
        canvas.drawLine(0,y,y/50,y-y/50,mPaint);
        canvas.drawLine(0,y,-y/50,y-y/50,mPaint);

        for (int i=0; i<mLineDatas.size(); i++){
            LineData mLineData = mLineDatas.get(i);
            mPath.incReserve(mLineData.getValue().length);//为添加更多点准备路径,可以更有效地分配其存储的路径
            for (int j=0; j< mLineData.getValue().length; j++){
                float currentVertical;
                if (animatedValue<y/2){
                    currentVertical = animatedValue;
                }else {
                    if ((mLineData.getValue()[j][1]-coordinate[3])*timesY >= y/2){
                        switch (flag){
                            case 0://同时到达
                                currentVertical = y/2+((mLineData.getValue()[j][1]-coordinate[3])*timesY-y/2)/y*2*(animatedValue-y/2);
                                break;
                            default://不同时到达
                                currentVertical = Math.min(animatedValue,(mLineData.getValue()[j][1]-coordinate[3])*timesY);
                                break;
                        }
//                        currentVertical = y/2+((mLineData.getValue()[j][1]-coordinate[3])*timesY-y/2)/y*2*(animatedValue-y/2);
                    }else {
                        switch (flag){
                            case 0:
                                currentVertical = y/2-(y/2-(mLineData.getValue()[j][1]-coordinate[3])*timesY)/y*2*(animatedValue-y/2);
                                break;
                            default:
                                currentVertical = Math.max(y-animatedValue,(mLineData.getValue()[j][1]-coordinate[3])*timesY);
                                break;
                        }
//                        currentVertical = y/2-(y/2-(mLineData.getValue()[j][1]-coordinate[3])*timesY)/y*2*(animatedValue-y/2);
                    }
                }
                if (j==0){
                    mPath.moveTo((mLineData.getValue()[j][0]-coordinate[0])*timesX,currentVertical);
                }else {
                    mPath.lineTo((mLineData.getValue()[j][0]-coordinate[0])*timesX,currentVertical);
                }
                mPaint.setStrokeWidth(1);
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setColor(mLineDatas.get(i).getColor());
                canvas.drawCircle((mLineData.getValue()[j][0]-coordinate[0])*timesX,currentVertical,x/70,mPaint);
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setColor(Color.WHITE);
                canvas.drawCircle((mLineData.getValue()[j][0]-coordinate[0])*timesX,currentVertical,x/100,mPaint);
            }
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(2);
            mPaint.setColor(mLineDatas.get(i).getColor());
            canvas.drawPath(mPath,mPaint);
            mPath.rewind();//清除
        }
    }

    private void initAnimator(long duration, float y){
        if (animator !=null &&animator.isRunning()){
            animator.cancel();
            animator.start();
        }else {
            animator= ValueAnimator.ofFloat(0,y).setDuration(duration);
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
     * 计算坐标轴
     * @param j
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
     * @param max
     * @param min
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

    public void setLineDatas(ArrayList<LineData> lineDatas) {
        mLineDatas = lineDatas;
        initCoordinate(mLineDatas);
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}