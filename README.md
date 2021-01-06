# switcher
最喜欢的自定义控件

- 此次更新优化字体相关设置，用起来更随心，更舒服。


### 引入控件
最新版本:  [![](https://jitpack.io/v/pichsy/switcher.svg)](https://jitpack.io/#pichsy/xwidget)

    
        
       implementation 'com.github.pichsy:switcher:1.0'
       
       


## 升级内容，新增字体全局设置。
- 注意：凡是继承自xwidget的基础类的textview都可以实现字体变更
1. 在xml中

    
    
    ```
      <com.pichs.switcher.Switcher
        android:layout_width="80dp"
        android:layout_height="40dp"
        app:xp_bgColor_switchOn="#00C853"
        app:xp_iconColor_switch="#ffffff"
        app:xp_bgColor_switchOff="#EB17C1"/>
        
    ```
## 感谢

此库摘录自：[Switcher](https://github.com/bitvale/Switcher/tree/1.1.1)
并修改了属性的使用方式，更人性化，减少属性使用。