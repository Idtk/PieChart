# CustomView<br>
 显示数据百分比的环形图<br>
## Usage<br>
* 新建Pieview<br>
```java
PieView mPieView = new PieView(this);
```
* 设置初始角度<br>
```java
mPieView.setStartAngle(0);
```
* 设置数据<br>
```java
mPieView.setPieData(mPieDatas);
//初始化mPieView
private void initData(){
        for (int i=0; i<9; i++){
            PieData pieData = new PieData();
            pieData.setValue(i+1);
            pieData.setColor(mColors[i]);
            Log.i("colorOld",Integer.toHexString(mColors[i]));
            mPieDatas.add(pieData);
        }
    }
```
## More<br>
* PieData
```java
mPieDatas.setName(String name);
mPieDatas.setValue(float value);
mPieDatas.setColor(int color);
```
* PieView
```java
/**
     * 设置起始角度
     * @param mStartAngle 起始角度
     */
    public void setStartAngle(float mStartAngle) {
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
```
## [Download](https://github.com/Idtk/CustomView/blob/master/jar/PieView.jar)<br>
## [Demo](https://github.com/Idtk/CustomView/tree/master/demo/PieViewDemo)<br>
<img src="https://github.com/Idtk/CustomView/blob/master/gif/CustomView.gif" alt="GitHub" title="GitHub,Social Coding"/><br>
