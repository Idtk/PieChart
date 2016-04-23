package com.example.administrator.customview.LineChart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;

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

    public LineChart(Context context) {
        super(context);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

    }

    public LineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    public LineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(5f);
        float x = mWidth*3/5;
        float y = mHeight*3/5;
        canvas.translate(mWidth/5,mHeight*4/5);
        canvas.scale(1,-1);
        canvas.drawLine(0,0,x,0,mPaint);
        canvas.drawLine(0,0,0,y,mPaint);
//        canvas.drawLine(x/3,0,x/3,y,mPaint);
        int timesX = (int) Math.floor((x-x/50)/(coordinate[1]-coordinate[0]));
        Log.i("TAG1", Arrays.toString(coordinate));
        for (int i=0;(coordinate[2]*i+coordinate[0])<=coordinate[1];i++){
            canvas.drawLine((coordinate[2]*i)*timesX,0,
                    (coordinate[2]*i)*timesX,y/50,mPaint);
            Log.i("TAGX",(coordinate[2]*i)*timesX+"");
        }
        canvas.drawLine(x,0,x-x/50,x/50,mPaint);
        canvas.drawLine(x,0,x-x/50,-x/50,mPaint);
        int timesY = (int) Math.floor((y-y/50)/(coordinate[4]-coordinate[3]));
        for (int i=0;(coordinate[5]*i+coordinate[3])<=coordinate[4];i++){
            canvas.drawLine(0,(coordinate[5]*i)*timesY,
                    x/50,(coordinate[5]*i)*timesY,mPaint);
            Log.i("TAGY",(coordinate[5]*i)*timesY+"");
        }
        canvas.drawLine(0,y,y/50,y-y/50,mPaint);
        canvas.drawLine(0,y,-y/50,y-y/50,mPaint);
        super.onDraw(canvas);
    }

    public void setLineDatas(ArrayList<LineData> lineDatas) {
        mLineDatas = lineDatas;
        initCoordinate(mLineDatas);
        invalidate();
    }

    /**
     * 计算坐标系
     */
    private void initCoordinate(ArrayList<LineData> mLineDatas){
        for (int i=0; i<mLineDatas.size();i++){
            LineData mLineData = mLineDatas.get(i);
            int length = mLineData.getValue().length;
            float maxX = mLineData.getValue()[0][0];
            float minX = mLineData.getValue()[length-1][0];
            float maxY = mLineData.getValue()[0][1];
            float minY = mLineData.getValue()[0][1];
            float scalingX = (float) 0.5;
            float scalingY = (float) 0.5;
            initMaxMin(mLineData,maxX,minX,scalingX,true,i);
            initAxis(1,mLineData,maxY,minY,scalingY,i);
        }
//        Log.i("TAG2", Arrays.toString(coordinate));
    }

    /**
     * 计算坐标轴
     * @param j
     */
    private void initAxis(int j, LineData mLineData,float max, float min, float scaling, int count){
        for (int i=1; i<mLineData.getValue().length;i++){
            max = Math.max(mLineData.getValue()[i][j],max);
            min = Math.min(mLineData.getValue()[i][j],min);
        }
        initMaxMin(mLineData,max,min,scaling,false,count);
    }

    /**
     * 计算最大最小整数值、区间量
     * @param mLineData
     * @param max
     * @param min
     * @param scaling
     */

    private void initMaxMin(LineData mLineData,float max, float min, float scaling, boolean flag, int count){
        max = (float) Math.ceil(max);
        min = (float) Math.floor(min);
        if ((max-min)/(mLineData.getValue().length-1)>=1){
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
            scaling = 5;
        }
        if (count != 0){
            if (!flag){
                coordinate[0] = coordinate[0]> min?min:coordinate[0];
                coordinate[1] = coordinate[1]< max?max:coordinate[1];
                coordinate[2] = coordinate[2]< scaling?scaling:coordinate[2];
//                Log.i("TAGX",min+":"+max+":"+scaling);
            }else {
                coordinate[3] = coordinate[3]> min?min:coordinate[3];
                coordinate[4] = coordinate[4]< max?max:coordinate[4];
                coordinate[5] = coordinate[5]< scaling?scaling:coordinate[5];
//                Log.i("TAGY",min+":"+max+":"+scaling);
            }
        }else {
            if (!flag){
                coordinate[0] = min;
                coordinate[1] = max;
                coordinate[2] = scaling;
//                Log.i("TAGX",min+":"+max+":"+scaling);
            }else {
                coordinate[3] = min;
                coordinate[4] = max;
                coordinate[5] = scaling;
//                Log.i("TAGY",min+":"+max+":"+scaling);
            }
        }
    }
}
