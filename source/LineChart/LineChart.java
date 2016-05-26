package com.customview.LineChart;

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
import android.view.MotionEvent;
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
    //动画
    private ValueAnimator animator = new ValueAnimator();
    private float animatedValue;
    private TimeInterpolator timeInterpolator = new DecelerateInterpolator();
    private long animatorDuration = 4000;
    private PathEffect mPathEffect = new DashPathEffect(new float[]{20,10},1);
    private Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.ITALIC);
    //是否同时到达
    private int flag = 0;
    //长宽比例
    private float horizontal;
    private float vertical;
    private float timesX;
    private float timesY;
    private int axisSize = 28;
    private float[] coordinate = new float[8];
    //X轴刻度值字符长度
    private NumberFormat numberFormat;
    //是否循环收敛
    private boolean convergenceFlag = true;
    private int decimalPlaces = 0;
    //坐标单位
    private String axisX = "";
    private String axisY = "";
    //坐标系
    private Paint mPaint = new Paint();
    private Path mPath = new Path();
    private int mXYColor = Color.DKGRAY;
    //曲线
    private Path cubicPath = new Path();
    private Paint linePaint = new Paint();
    private float intensity = 0.2f;//Bezier 强度
    private int mCublicColor = Color.BLACK;
    //touch
    private boolean touchFlag = true;
    private int touchId = 0;
    private int touchArrayId = 0;
    private Paint touchPaint = new Paint();
    //点
    private Paint pointPaint = new Paint();
    private int pointSize = 30;

    public LineChart(Context context) {
        super(context);
        initPaint();
    }

    public LineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public LineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        touchId = mLineDatas.get(0).getValue().length;
        initAnimator(animatorDuration,mHeight*3/5);
        horizontal = mWidth*3/5;
        vertical = mHeight*3/5;
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTypeface(font);
        //设置小数点位数
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(decimalPlaces);
        if (convergenceFlag){
            //循环收敛
            convergence();
        }
        timesX = (horizontal-(horizontal/50))/(coordinate[1]-coordinate[0]);
        timesY = (vertical-(vertical/50))/(coordinate[4]-coordinate[3]);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(mWidth/5,mHeight*4/5);
        canvas.scale(1,-1);//翻转Y轴
        canvas.drawLine(0,0,horizontal,0,mPaint);
        canvas.drawLine(0,0,0,vertical,mPaint);
        //X轴
        for (int i=0;(coordinate[2]*i+coordinate[0])<=coordinate[1];i++){
            canvas.drawLine((float) (coordinate[2]*i*timesX),0, (float) (coordinate[2]*i*timesX),
                    -vertical/50,mPaint);
            canvas.save();
            canvas.scale(1,-1);
            //根据Paint的TextSize计算X轴的值
            float TextPathX = (float) (coordinate[2]*i*timesX);
            float TextPathY = (mPaint.descent()+mPaint.ascent())-vertical/50;
            canvas.drawText(numberFormat.format(coordinate[2]*i+coordinate[0]), TextPathX,-TextPathY,mPaint);
            canvas.restore();
        }
        canvas.drawLine(horizontal,0,horizontal-horizontal/50,horizontal/50,mPaint);
        canvas.drawLine(horizontal,0,horizontal-horizontal/50,-horizontal/50,mPaint);
        canvas.save();
        canvas.scale(1,-1);
        canvas.drawText(axisX,horizontal,(mPaint.descent()+mPaint.ascent())-vertical/50,mPaint);
        canvas.restore();


        //Y轴
        for (int i=0;(coordinate[5]*i+coordinate[3])<=coordinate[4];i++){
            //坐标轴刻度
            canvas.save();
            canvas.scale(1,-1);
            //根据Paint的TextSize计算X轴的值
            float TextPathX = mPaint.measureText(coordinate[5]*1+coordinate[3]+"");
            float TextPathY = (mPaint.descent()+mPaint.ascent())/2+(float) (coordinate[5]*i*timesY);
            canvas.drawText(numberFormat.format(coordinate[5]*i+coordinate[3]),-TextPathX,-TextPathY,mPaint);
            canvas.restore();
        }
        canvas.save();
        canvas.scale(1,-1);
        canvas.drawText(axisY,0,-vertical+(mPaint.descent()+mPaint.ascent())*2,mPaint);
        canvas.restore();

        mPath.rewind();//清除
        mPaint.setAntiAlias(true);
        canvas.drawLine(0,vertical,vertical/50,vertical-vertical/50,mPaint);
        canvas.drawLine(0,vertical,-vertical/50,vertical-vertical/50,mPaint);

        drawLine(canvas);
    }

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

    private void initPaint(){
        //初始画笔
        mPaint.setStrokeWidth(1);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mXYColor);
        mPaint.setTextSize(axisSize);
        linePaint.setStrokeWidth(5);
        linePaint.setAntiAlias(true);
        linePaint.setColor(mCublicColor);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        pointPaint.setStrokeWidth(2);
        pointPaint.setStyle(Paint.Style.FILL);
        touchPaint.setTextAlign(Paint.Align.CENTER);
        touchPaint.setTextSize(pointSize);
        touchPaint.setStrokeWidth(1);
        touchPaint.setStyle(Paint.Style.FILL);
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

    private void convergence(){
        int count = 0;
        int newCount = 0;
        initScaling(coordinate[0],coordinate[1],mLineDatas.get(0).getValue().length,false);
        //二次处理字符过长
        while ((coordinate[2]*count+coordinate[0])<=coordinate[1]){
            count++;
        }
        float stringLength = mPaint.measureText(numberFormat.format(coordinate[2]*1+coordinate[0]));
        while (count*stringLength>horizontal){
            count = count/2;
            newCount++;
        }
        coordinate[2] = newCount!=0 ? coordinate[2]*newCount*2:coordinate[2];
        //收敛
        while (coordinate[6]-coordinate[0]>coordinate[2]){
            coordinate[0] += coordinate[2];
        }

        while (coordinate[1]-coordinate[7]>coordinate[2]){
            coordinate[1] -= coordinate[2];
        }

        if (coordinate[1]-coordinate[0]<=(coordinate[2]*2)){
            convergence();
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
            initMaxMin(maxX,minX,false,i,mLineDatas.get(0).getValue().length);

            /*for (int j=0; j<mLineData.getValue().length; j++){
                Log.i("TAG1",(mLineData.getValue()[j][0])+":"+(mLineData.getValue()[j][1])+":"+i);
            }*/
            coordinate[6] = minX;
            coordinate[7] = maxX;
        }
        //默认所有的LineData。getValue()长度相同
        initScaling(coordinate[0],coordinate[1],mLineDatas.get(0).getValue().length,false);
        initScaling(coordinate[3],coordinate[4],mLineDatas.get(0).getValue().length,true);

//        Log.i("TAG1", Arrays.toString(coordinate));
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
        initMaxMin(max,min,true,count,mLineDatas.get(0).getValue().length);
    }

    /**
     * 计算最大最小整数值、区间量
     * @param max 最大值
     * @param min 最小值
     * @param flag X or Y 轴
     * @param count 第几组数据
     */
    private void initMaxMin(float max, float min, boolean flag, int count,int length){
        /*float scaling;
        if (length<11){
            scaling = (max-min)/length;
        }else {
            scaling = (max-min)/10;
        }*/
        int number = 0;
        //判断刻度值精度
        if (max>1){
            while (max>10){
                max=max/10;
                number++;
            }
            max = (float) (Math.ceil(max)*Math.pow(10,number));
            min = (float) (Math.floor(min/Math.pow(10,number))*Math.pow(10,number));
//            scaling = (float) (Math.ceil(scaling/Math.pow(10,number))*Math.pow(10,number));
        }else {
            while (max<1){
                max=max*10;
                number++;
            }
            max = (float) (Math.ceil(max)*Math.pow(10,-number));
            min = (float) (Math.floor(min/Math.pow(10,-number))*Math.pow(10,-number));
//            scaling = (float) (Math.ceil(scaling/Math.pow(10,-number))*Math.pow(10,-number));
//            decimalPlaces=number;
        }

        /*if (!flag){
            coordinate[2] = scaling;
        }else {
            coordinate[5] = scaling;
        }*/
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
        float scaling;
        int count = 0;
        //初步计算刻度值
        if (length<11){
            scaling = (max-min)/length;
        }else {
            scaling = (max-min)/10;
        }
        //判断刻度值精度
        if (scaling>1){
            while (scaling>10){
                scaling=scaling/10;
                count++;
            }
            scaling = (float) (Math.ceil(scaling)*Math.pow(10,count));
        }else {
            while (scaling<1){
                scaling=scaling*10;
                count++;
            }
            scaling = (float) (Math.ceil(scaling)*Math.pow(10,-count));
            decimalPlaces=count;
        }
        if (!flag){
            coordinate[2] = scaling;
        }else {
            coordinate[5] = scaling;
        }
    }


    private void drawTouch(float x, float y){
        double minValue = horizontal;
        int minI =mLineDatas.size();
        int minJ =mLineDatas.get(0).getValue().length;
        for (int i=0; i< mLineDatas.size(); i++){
            for (int j=0; j< mLineDatas.get(i).getValue().length; j++){
                if (Math.abs((mLineDatas.get(i).getValue()[j][0]-coordinate[0])*timesX-x)<(horizontal/50)||
                        (Math.abs(mLineDatas.get(i).getValue()[j][1]-coordinate[3])*timesY-y)<(horizontal/50)){
                    float gapX = (float) ((mLineDatas.get(i).getValue()[j][0]-coordinate[0])*timesX-x);
                    float gapY = (float) ((mLineDatas.get(i).getValue()[j][1]-coordinate[3])*timesY-y);
                    double radius = Math.hypot(gapX,gapY);
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

    private void drawLine(Canvas canvas){
        for (int i=0; i<mLineDatas.size(); i++){
            if (i==touchArrayId && touchId < mLineDatas.get(0).getValue().length){
                LineData mLineDataPoint = mLineDatas.get(i);
                Paint.FontMetrics fontMetrics = touchPaint.getFontMetrics();
                canvas.save();
                canvas.scale(1,-1);
                touchPaint.setColor(mXYColor);
                canvas.drawText("("+(mLineDataPoint.getValue()[touchId][0])+
                                ", "+mLineDataPoint.getValue()[touchId][1]+")",
                        (mLineDataPoint.getValue()[touchId][0]-coordinate[0])*timesX,
                        -(mLineDataPoint.getValue()[touchId][1]-coordinate[3])*timesY+(fontMetrics.top
                                +fontMetrics.bottom)/2,touchPaint);
                canvas.restore();
            }
            LineData mLineData = mLineDatas.get(i);
            mPath.incReserve(mLineData.getValue().length);//为添加更多点准备路径,可以更有效地分配其存储的路径
            for (int j=0; j< mLineData.getValue().length; j++){
                float currenthorizontal;
                if (animatedValue<vertical/2){
                    currenthorizontal = animatedValue;
                }else {
                    if ((mLineData.getValue()[j][1]-coordinate[3])*timesY >= vertical/2){
                        switch (flag){
                            case 0://同时到达
                                currenthorizontal = vertical/2+((mLineData.getValue()[j][1]-coordinate[3])*timesY
                                        -vertical/2)/vertical*2*(animatedValue-vertical/2);
                                break;
                            default://先后到达
                                currenthorizontal = Math.min(animatedValue,
                                        (mLineData.getValue()[j][1]-coordinate[3])*timesY);
                                break;
                        }
                    }else {
                        switch (flag){
                            case 0:
                                currenthorizontal = vertical/2-(vertical/2-
                                        (mLineData.getValue()[j][1]-coordinate[3])*timesY)/
                                        vertical*2*(animatedValue-vertical/2);
                                break;
                            default:
                                currenthorizontal = Math.max(vertical-animatedValue,
                                        (mLineData.getValue()[j][1]-coordinate[3])*timesY);
                                break;
                        }
                    }
                }
                if (j==0){
                    mPath.moveTo((mLineData.getValue()[j][0]-coordinate[0])*timesX,currenthorizontal);
                }else {
                    mPath.lineTo((mLineData.getValue()[j][0]-coordinate[0])*timesX,currenthorizontal);
                }
                pointPaint.setColor(mLineDatas.get(i).getColor());
                canvas.drawCircle((mLineData.getValue()[j][0]-coordinate[0])*timesX,currenthorizontal,horizontal/70,pointPaint);
                pointPaint.setColor(Color.WHITE);
                canvas.drawCircle((mLineData.getValue()[j][0]-coordinate[0])*timesX,currenthorizontal,horizontal/100,pointPaint);
            }
            linePaint.setColor(mLineData.getColor());
            canvas.drawPath(mPath,linePaint);
            mPath.rewind();//清除
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


