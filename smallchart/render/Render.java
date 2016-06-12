package com.idtk.smallchart.render;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

/**
 * Created by Idtk on 2016/6/6.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 */
public abstract class Render {
    public Render() {
        super();
    }
//    protected Paint paint = new Paint();
    /*protected ChartData data;
    public ChartData getData(){
        return data;
    }
    public void setData(ChartData data){
        this.data = data;
    }*/
    protected void textCenter(String[] strings, Paint paint, Canvas canvas, PointF point, Paint.Align align){
        paint.setTextAlign(align);
        Paint.FontMetrics fontMetrics= paint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        int length = strings.length;
        float total = (length-1)*(-top+bottom)+(-fontMetrics.ascent+fontMetrics.descent);
        float offset = total/2-bottom;
        for (int i = 0; i < length; i++) {
            float yAxis = -(length - i - 1) * (-top + bottom) + offset;
            canvas.drawText(strings[i], point.x, point.y + yAxis, paint);
//            Log.d("TAG",paint.measureText(strings[i])+":"+strings[i]);
        }
    }
}
