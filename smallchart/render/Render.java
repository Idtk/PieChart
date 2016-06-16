package com.idtk.smallchart.render;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

/**
 * Created by Idtk on 2016/6/6.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 ; 渲染基类
 */
public abstract class Render {
    public Render() {
        super();
    }

    /**
     * 多行文本居中、居右、居左
     * @param strings 文本字符串列表
     * @param paint 画笔
     * @param canvas 画布
     * @param point 点的坐标
     * @param align 居中、居右、居左
     */
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
        }
    }
}
