package com.idtk.smallchart.render;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

import com.idtk.smallchart.data.BarData;
import com.idtk.smallchart.data.XAxisData;
import com.idtk.smallchart.data.YAxisData;

import java.text.NumberFormat;

/**
 * Created by Idtk on 2016/6/6.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 ; 柱状图渲染类
 */
public class BarChartRender extends ChartRender<BarData> {

    private BarData barData;
    private Path barPath = new Path();
    private Paint barPaint = new Paint();
    private XAxisData xAxisData = new XAxisData();
    private YAxisData yAxisData = new YAxisData();
    private float offset;
    private PointF mPointF = new PointF();
    private Paint.FontMetrics fontMetrics;
    private float barWidth;
    /*private static final int LAYER_FLAGS = Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG |
            Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG;*/
    private static final int LAYER_FLAGS = Canvas.ALL_SAVE_FLAG;

    public BarChartRender(BarData barData, XAxisData xAxisData, YAxisData yAxisData,float offset,float barWidth) {
        super();
        this.barData = barData;
        this.xAxisData = xAxisData;
        this.yAxisData = yAxisData;
        this.offset = offset;
        this.barWidth = barWidth;
        barPaint.setStyle(Paint.Style.FILL);
        barPaint.setAntiAlias(true);
        barPaint.setStrokeWidth(barData.getPaintWidth());
        barPaint.setTextSize(barData.getTextSize());
    }

    @Override
    public void drawGraph(Canvas canvas, float animatedValue) {
        float currentXAxis,currentYAxis;
        /**
         * 为添加更多点准备路径,可以更有效地分配其存储的路径
         */
        barPath.incReserve(barData.getValue().size());
        for (int j=0; j<barData.getValue().size(); j++){
            currentXAxis = (barData.getValue().get(j).x-xAxisData.getMinimum())*xAxisData.getAxisScale()+offset;
            currentYAxis = -(barData.getValue().get(j).y-yAxisData.getMinimum())*yAxisData.getAxisScale()*animatedValue;
            barPath.addRect(currentXAxis,currentYAxis,currentXAxis+barWidth,0, Path.Direction.CW);
            fontMetrics= barPaint.getFontMetrics();
            mPointF.x = currentXAxis+barWidth/2;
            mPointF.y = currentYAxis+(fontMetrics.top-fontMetrics.bottom);
            NumberFormat numberFormatY = NumberFormat.getNumberInstance();
            numberFormatY.setMaximumFractionDigits(yAxisData.getDecimalPlaces());
            barPaint.setColor(xAxisData.getColor());
<<<<<<< HEAD
            if (barData.isTextSize)
                textCenter(new String[]{numberFormatY.format(barData.getValue().get(j).y)},barPaint,canvas,mPointF, Paint.Align.CENTER);
=======
            textCenter(new String[]{numberFormatY.format(barData.getValue().get(j).y)},barPaint,canvas,mPointF, Paint.Align.CENTER);
>>>>>>> origin/master
        }
        /**
         * 柱状图图层为0x80半透明状态
         */
        canvas.saveLayerAlpha(-canvas.getWidth()+xAxisData.getAxisLength(), -yAxisData.getAxisLength(), xAxisData.getAxisLength(),canvas.getHeight()-yAxisData.getAxisLength(), 0x80, LAYER_FLAGS);
        barPaint.setColor(barData.getColor());
<<<<<<< HEAD
=======
        canvas.saveLayerAlpha(-canvas.getWidth()+xAxisData.getAxisLength(), -yAxisData.getAxisLength(), xAxisData.getAxisLength(),canvas.getHeight()-yAxisData.getAxisLength(), 0x80, Canvas.ALL_SAVE_FLAG);
>>>>>>> origin/master
        canvas.drawPath(barPath,barPaint);
        canvas.restore();
        barPath.reset();
    }
}
