# RulerView
## 前言
一开始只是打算简单写一个控件参加[HenCoder「仿写酷界面」活动——征稿](http://hencoder.com/activity-mock-1/)这活动的，后来写着写着，哎呀，这功能必须加，如果是这样，我可不能接受,于是改着改着，就有了这篇文章。

## 目前可提供的配置的属性
<table>
        <tr>
            <th>name</th>
            <th>format</th>
            <th>default</th>
            <th>备注</th>
        </tr>
        <tr>
            <th>indicateColor</th>
            <th>color|reference</th>
            <th>Color.BLACK</th>
            <th>指示器颜色</th>
        </tr>
        <tr>
            <th>indicateWidth</th>
            <th>dimension|reference</th>
            <th>12dp</th>
            <th>指示器的宽度</th>
        </tr>
        <tr>
             <th>indicateHeight</th>
            <th>dimension|reference</th>
            <th>24dp</th>
            <th>指示器高度</th>
        </tr>
    <tr>
             <th>smallIndicateColor</th>
            <th>color|reference</th>
            <th>Color.BLACK</th>
            <th>小指示器的颜色</th>
        </tr>
      <tr>
             <th>smallIndicateWidth</th>
            <th>dimension|reference</th>
            <th>10dp</th>
            <th>小指示器宽度</th>
        </tr>
      <tr>
             <th>smallIndicateHeight</th>
            <th>dimension|reference</th>
            <th>10dp</th>
            <th>小指示器高度</th>
        </tr>
      <tr>
             <th>textColor</th>
            <th>color|reference</th>
            <th>Color.BLACK</th>
            <th>字体颜色</th>
        </tr>
      <tr>
             <th>textSize</th>
            <th>dimension|reference</th>
            <th>12dp</th>
            <th>字体大小</th>
        </tr>
    <tr>
             <th>indicateMarginText</th>
            <th>dimension|reference</th>
            <th>16dp</th>
            <th>指示器距离文字的距离</th>
        </tr>
    <tr>
             <th>isDrawText</th>
            <th>boolean</th>
            <th>true</th>
            <th>是否绘制文字</th>
        </tr>
    <tr>
             <th>startIndex</th>
            <th>integer|reference</th>
            <th>0</th>
            <th>开始下标</th>
        </tr>
      <tr>
             <th>endIndex</th>
            <th>integer|reference</th>
            <th>100</th>
            <th>结束下标</th>
        </tr>
      <tr>
             <th>smallIndicateCount</th>
            <th>integer|reference</th>
            <th>4</th>
            <th>大指示器之间间隔多少个小的</th>
        </tr>
      <tr>
             <th>indicatePadding</th>
            <th>dimension|reference</th>
            <th>12dp</th>
            <th>指示器之间的距离</th>
        </tr>
      <tr>
             <th>orientation</th>
            <th>enum(horizontal||vertical)</th>
            <th>horizontal</th>
            <th>尺子的方向</th>
        </tr>
    <tr>
             <th>gravity</th>
            <th>enum(top||bottom)</th>
            <th>bottom</th>
            <th>文字位置相对尺子的位置</th>
        </tr>
         <tr>
          <td colspan="4" >多指触控支持</td>
        </tr>
    </table>


## 一睹为快
![这里写图片描述](http://img.blog.csdn.net/20171019231451353?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMzY1ODM3NA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
更多预览图正在制作上传中
## 在计划中的功能
- [ ] 适配wrap_content
- [ ] 添加springBack功能
- [ ] 支持代码设置状态
- [ ] 添加状态接口回调
- [ ] 自定义准线位置(现在默认是屏幕中间)

## 已知问题
因为RulerView第一进入就会把所有刻度绘制出来，多出来的部分只是屏幕未能显示出来，所以这样就会导致一个问题，如果绘制的内容很多(也就是说大范围)，很容易卡顿，因为是RulerView是继承自View,所以没有做回收机制，同时RulerView的初衷就应该是小范围选择，当然了这个问题，在做的时候，曾经想过办法解决，但还在想的阶段，1.使用Canvas clip 动态限制绘制区域 2.动态绘制屏幕区域(类似与常见的绘制动态滚动圆弧做法) 3.重做，用RecyclerView+自定义LayoutManager。



