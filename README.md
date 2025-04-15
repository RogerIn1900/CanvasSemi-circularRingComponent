## CanvasSemi-circularRingComponent

一个用于健康数据展示的动画半圆环图表组件，支持自定义样式和交互。

### 效果
![image](https://github.com/user-attachments/assets/b8982a52-9db5-4bb9-b4e9-041d9288ecf0)

### 调用

```
SemiCircularRing(
    calories: Int = 691,  //最外圈
    steps: Int = 10135,   //中间圈
    duration: Int = 55,   //最内圈
    modifier: Modifier = Modifier  //compose相关的调整
)
```

### 绘制逻辑

- 获取页面的大小后，通过 translate() 方法将图像移到画面中间
- 先绘制底层半圆环
- 再对当前数值进行判断：
    -  与标准值的比值在0到1之间就根据当前的比值绘制第一层的弧度
    -  与标准值的比值大于1就将第一层绘制满，在将浅色半圆环根据取模后的部分绘制相应的角度，并绘制箭头

### 组件说明

- 基本工具：使用Canvas绘制
- 绘制样式：使用 Paint().apply 进行自定义
- 半圆环：
     -   底层：在 drawIntoCanvas 组件里面使用 canvas.nativeCanvas.drawArc 绘制
     -   真实数值部分： 在底层代码的基础上，更换颜色和绘制的角度
     -   超出部分： 在真实数值部分全满的基础上，将颜色调整为浅色，并更换角度
- 箭头： 
     -   在计算箭头两条线的左右两端后，使用 drawScope.drawLine 进行绘制
    
