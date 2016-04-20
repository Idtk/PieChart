# CustomView<br>
## Introduction<br>
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
* PieView更多设置请查看源码<br>
* [Download](https://github.com/Idtk/CustomView/blob/master/jar/)<br>
* [Demo](https://github.com/Idtk/CustomView/tree/master/demo/PieViewDemo)<br>
<img src="https://github.com/Idtk/CustomView/blob/master/gif/CustomView.gif" alt="GitHub" title="GitHub,Social Coding"/><br>

## Reminders<br>
 环形块显示的名字,建议不超过4个中文字符
