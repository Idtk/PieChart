# CustomView<br>

# **此项目已合并至[SmallChart](https://github.com/Idtk/SmallChart)**

## Introduction<br>
&nbsp;&nbsp;&nbsp;&nbsp;可定义环形块颜色与名称，显示数据百分比的环形图。用户可自定义动画效果，内外圆半径比，百分比小数位，画笔颜色，字体大小等。<br>

## Version
SdkVersion >= 19

## Usage<br>
* 新建Pieview<br>
```java
PieView mPieView = new PieView(this);
```
or
```Java
<com.example.administrator.customviewdeom.PieChart.PieChart
        android:id="@+id/pieChart"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:textSize="20dp"
        app:name="Demo"/>
        
PieChart pieChart = (PieChart) findViewById(R.id.pieChart);
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
            pieData.setName("区域名);
            pieData.setValue(i+1);
            pieData.setColor(mColors[i]);
            Log.i("colorOld",Integer.toHexString(mColors[i]));
            mPieDatas.add(pieData);
        }
    }
```

## More<br>
* PieView更多设置请查看源码<br>

## Demo<br>
<img src="https://github.com/Idtk/CustomView/blob/master/gif/PieChart.gif" alt="PieChart" title="PieChart"/><br>

******
## About

&nbsp;&nbsp;**博客: www.idtkm.com**<br>
&nbsp;&nbsp;**GitHub: https://github.com/Idtk**<br>
&nbsp;&nbsp;**微博: http://weibo.com/Idtk**<br>
&nbsp;&nbsp;**邮箱: IdtkMa@gmail.com**<br>
&nbsp;&nbsp;**稀土掘金: http://gold.xitu.io/user/56ff44087db2a20059ff7913**<br>
&nbsp;&nbsp;**开发者头条: https://toutiao.io/subjects/89638**<br>
