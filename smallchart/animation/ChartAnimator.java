package com.idtk.smallchart.animation;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by Idtk on 2016/6/8.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 生成动画帮助类
 */
public class ChartAnimator {

    private ValueAnimator animator = new ValueAnimator();
    private ValueAnimator.AnimatorUpdateListener listener;
    private TimeInterpolator timeInterpolator;

    public ChartAnimator(ValueAnimator.AnimatorUpdateListener listener, TimeInterpolator timeInterpolator) {
        super();
        this.listener = listener;
        this.timeInterpolator = timeInterpolator;
    }
    public ChartAnimator(ValueAnimator.AnimatorUpdateListener listener) {
        this(listener,new DecelerateInterpolator());
    }

    /**
     * 使用时，需要 invalidate()
     * @param duration 动画时间
     * @param y 动画运动目标值
     */
    public void animatedY(long duration, float y){
        if (animator !=null &&animator.isRunning()){
            animator.cancel();
            animator.start();
        }else {
            animator= ValueAnimator.ofFloat(0,y).setDuration(duration);
            animator.setInterpolator(timeInterpolator);
            animator.addUpdateListener(listener);
            animator.start();
        }
    }
}
