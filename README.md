# RulerView
丰富的样式可供配置
```
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <declare-styleable name="RulerView">
        <!--指示器颜色-->
        <attr name="indicateColor" format="color|reference"/>
        <!--字体颜色-->
        <attr name="textColor" format="color|reference"/>
        <!--字体大小-->
        <attr name="textSize" format="dimension|reference"></attr>
        <!--指示器距离文字的距离-->
        <attr name="indicateMarginText" format="dimension|reference"/>
        <attr name="isDrawText" format="boolean"/>
        <!--开始下标-->
        <attr name="startIndex" format="integer|reference"/>
        <!--结束下标-->
        <attr name="endIndex" format="integer|reference"/>
        <!--指示器的宽度-->
        <attr name="indicateWidth" format="dimension|reference"/>
        <!--指示器高度-->
        <attr name="indicateHeight" format="dimension|reference"/>
        <!--小指示器宽度-->
        <attr name="smallIndicateWidth" format="dimension|reference"/>
        <!--小指示器高度度-->
        <attr name="smallIndicateHeight" format="dimension|reference"/>
        <!--小指示器的颜色-->
        <attr name="smallIndicateColor" format="color|reference"/>
        <!--大指示器之间间隔多少个小的-->
        <attr name="smallIndicateCount" format="integer|reference"/>
        <!--指示器之间的距离-->
        <attr name="indicatePadding" format="dimension|reference"/>


        <!--尺子的方向-->
        <attr name="orientation">
            <enum name="horizontal" value="0"/>
            <enum name="vertical" value="1"/>
        </attr>

        <!--文字位置-->
        <attr name="gravity">
            <enum name="top" value="0"/>
            <enum name="bottom" value="1"/>
        </attr>


    </declare-styleable>
</resources>
```
浏览图正在制作上传中
