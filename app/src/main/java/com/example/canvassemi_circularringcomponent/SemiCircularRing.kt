package com.example.canvassemi_circularringcomponent

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.cos
import kotlin.math.sin


//状态提升
@Composable
fun SemiCircularRing(calories:Int = 691,steps:Int = 10135,midAcitivityTime:Int = 55,modifier: Modifier = Modifier) {
    val datas = listOf(
        calories / 400.0,
        steps / 6000.0,
        midAcitivityTime / 30.0
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
//            .height(150.dp) // 设置 Column 的大小为 300dp
//            .width(300.dp)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize() // Canvas 填满 Column 的空间
        ) {
            val canvasSize = size // Canvas 的尺寸
            val canvasWidth = canvasSize.width
            val canvasHeight = canvasSize.height

//            val ringWidth = 40.dp.toPx() // 半环宽度（转换为像素）
            val ringWidth = (size.width * 0.13f) // 半环宽度（转换为像素）
            val maxRadius = (canvasWidth / 2) - ringWidth / 2 // 最大半径，确保圆环不超出 Canvas

            //动态箭头大小、文字大小
            // 箭头长度动态计算（示例为 Canvas 宽度的 5%）
            val arrowHeadLength = size.width * 0.05f

            // 定义颜色和画笔
            val transparentPaints = listOf(
                Paint().apply {
                    color = Color(0x4DFF5722) // 棕色
                    strokeWidth = ringWidth
                    isAntiAlias = true
                    style = PaintingStyle.Stroke
//                    strokeCap = Round
                },
                Paint().apply {
                    color = Color(0x4DFFC107) // 另一种棕色
                    strokeWidth = ringWidth
                    isAntiAlias = true
                    style = PaintingStyle.Stroke
//                    strokeCap = Round
                },
                Paint().apply {
                    color = Color(0x4D2196F3) // 深蓝色
                    strokeWidth = ringWidth
                    isAntiAlias = true
                    style = PaintingStyle.Stroke
//                    strokeCap = Round
                }
            )


            val paints = listOf(
                Paint().apply {
                    color = Color(0xFFFF5722) // 棕色
                    strokeWidth = ringWidth
                    isAntiAlias = true
                    style = PaintingStyle.Stroke
//                    strokeCap = Round
                },
                Paint().apply {
                    color = Color(0xFFFFC107) // 另一种棕色
                    strokeWidth = ringWidth
                    isAntiAlias = true
                    style = PaintingStyle.Stroke
//                    strokeCap = Round
                },
                Paint().apply {
                    color = Color(0xFF2196F3) // 深蓝色
                    strokeWidth = ringWidth
                    isAntiAlias = true
                    style = PaintingStyle.Stroke
//                    strokeCap = Round
                }
            )

            //绘制三个半圆环的背景
            transparentPaints.forEachIndexed { index, paint ->
                val radius = maxRadius - index * ringWidth * 1.2f // 调整半径，确保圆环不重叠且不超出边界
                val sweepAngle = (datas[index] * 180).toFloat()

                // 绘制底层部分
                drawIntoCanvas { canvas ->
                    withTransform({
                        translate(canvasWidth / 2, canvasHeight) // 将坐标系移动到 Canvas 的中心
                    }) {
                        // 如果 sweepAngle 大于 180f，只绘制 180f 的部分
//                        val drawAngle = if (sweepAngle > 180f) 180f else sweepAngle
                        canvas.nativeCanvas.drawArc(
                            -radius, // 左边界
                            -radius, // 上边界
                            radius,  // 右边界
                            radius,  // 下边界
                            180f,   // 起始角度
                            180f,   // 扫过的角度
                            false,  // 不使用中心点连接
                            paint.asFrameworkPaint(), // 使用 Paint
                        )
                    }
                }
            }

            // 绘制三个半圆环的真实数值
            //一倍以内
            paints.forEachIndexed { index, paint ->
                val radius = maxRadius - index * ringWidth * 1.2f // 调整半径，确保圆环不重叠且不超出边界
                val sweepAngle = ((datas[index] % 180)* 180).toFloat()

                // 绘制底层部分
                drawIntoCanvas { canvas ->
                    withTransform({
                        translate(canvasWidth / 2, canvasHeight) // 将坐标系移动到 Canvas 的中心
                    }) {
                        // 如果 sweepAngle 大于 180f，只绘制 180f 的部分
                        val drawAngle = if (sweepAngle > 180f) 180f else sweepAngle
                        canvas.nativeCanvas.drawArc(
                            -radius, // 左边界
                            -radius, // 上边界
                            radius,  // 右边界
                            radius,  // 下边界
                            180f,   // 起始角度
                            drawAngle,   // 扫过的角度
                            false,  // 不使用中心点连接
                            paint.asFrameworkPaint(), // 使用 Paint
                        )
                    }
                }
            }
            // 1-2倍 大于2倍
            paints.forEachIndexed { index, paint ->
                val radius = maxRadius - index * ringWidth * 1.2f // 调整半径，确保圆环不重叠且不超出边界
                val sweepAngle = ((datas[index] % 180) * 180).toFloat()

                // 如果 sweepAngle 大于 180f，绘制上层部分
                if (sweepAngle > 180f) {
                    val drawAngle = sweepAngle % 180
                    val lightPaint = Paint().apply {
                        color = lightenColor(paint.color)// 使用原始颜色的浅色,传入的参数不能直接使用color，会变黑
                        strokeWidth = ringWidth
                        isAntiAlias = true
                        style = PaintingStyle.Stroke
//                        strokeCap = Round
                    }
                    drawIntoCanvas { canvas ->
                        withTransform({
                            translate(canvasWidth / 2, canvasHeight) // 将坐标系移动到 Canvas 的中心
                        }) {
                            // 绘制超过 180f 的部分
                            canvas.nativeCanvas.drawArc(
                                -radius, // 左边界
                                -radius, // 上边界
                                radius,  // 右边界
                                radius,  // 下边界
                                180f,   // 起始角度
                                drawAngle, // 扫过的角度（超过 180f 的部分）
                                false,  // 不使用中心点连接
                                lightPaint.asFrameworkPaint() // 使用浅色 Paint
                            )
                        }
                    }
                }
            }


            //箭头绘制
            paints.forEachIndexed { index, paint ->
                val radius = maxRadius - index * ringWidth * 1.2f // 调整半径，确保圆环不重叠且不超出边界
                val sweepAngle = ((datas[index] % 180)* 180).toFloat()

                //绘制小箭头
                val canvasSize = size
                val centerX = canvasSize.width / 2
                val centerY = canvasSize.height
//                    val radius = maxRadius - index * ringWidth * 2.05f // 旋转半径
                val startAngle = 180f // 起始角度（以度为单位）
                val sweepAngle2 = 180f+80f// 扫过的角度（以度为单位）
//                    val sweepAngle = sweepAngle - 180f - 25f  // 扫过的角度（以度为单位）



                // 如果 sweepAngle 大于 180f，绘制箭头
                if (sweepAngle > 180){
                    val drawAngle = sweepAngle % 180
                    // 计算圆弧的终点坐标
                    val endAngle = startAngle + drawAngle - (index +0.8f)*10f
                    val endAngleRadians = Math.toRadians(endAngle.toDouble())
                    val endX = centerX + radius * cos(endAngleRadians).toFloat()
                    val endY = centerY + radius * sin(endAngleRadians).toFloat()
                    // 绘制箭头
                    drawArrow(
                        drawScope = this, // 将 DrawScope 作为参数传递
                        startX = endX,
                        startY = endY,
                        angle = endAngle + 100f, // 箭头的旋转角度增加 90 度  箭头自身旋转
//                        arrowHeadLength = 30f, // 箭头头部的长度
                        arrowHeadLength = arrowHeadLength, // 箭头头部的长度
                        arrowHeadAngle = 30f, // 箭头头部的角度
//                        color = lightenColor(lightenColor(paint.color))
                        color = paint.color
//                        color = Color.White
                    )
                }
            }
        }

        // 将颜色变浅的函数

    }
}

fun lightenColor(color: Color): Color {
    val red = (color.red * 1.3f).coerceAtMost(1f) // 增加红色分量
    val green = (color.green * 1.3f).coerceAtMost(1f) // 增加绿色分量
    val blue = (color.blue * 1.3f).coerceAtMost(1f) // 增加蓝色分量
    return Color(red, green, blue, color.alpha) // 保持透明度不变
}
/**
 * 在指定位置绘制箭头
 *
 * @param drawScope DrawScope 对象，用于调用绘图方法
 * @param startX 箭头的起点 X 坐标
 * @param startY 箭头的起点 Y 坐标
 * @param angle 箭头的旋转角度（以度为单位）
 * @param arrowHeadLength 箭头头部的长度
 * @param arrowHeadAngle 箭头头部的角度（以度为单位）
 * @param color 箭头颜色
 */
fun drawArrow(
    drawScope: DrawScope,
    startX: Float,
    startY: Float,
    angle: Float,
    arrowHeadLength: Float,
    arrowHeadAngle: Float,
    color: Color = Color.Blue
) {
    // 计算箭头的终点坐标
    val angleRadians = Math.toRadians(angle.toDouble())
    val endX = startX + arrowHeadLength * cos(angleRadians).toFloat()
    val endY = startY + arrowHeadLength * sin(angleRadians).toFloat()

    // 计算箭头头部的两个端点
    val headAngleRadians = Math.toRadians(arrowHeadAngle.toDouble())
    val dx1 = arrowHeadLength * cos(angleRadians + headAngleRadians).toFloat()
    val dy1 = arrowHeadLength * sin(angleRadians + headAngleRadians).toFloat()
    val dx2 = arrowHeadLength * cos(angleRadians - headAngleRadians).toFloat()
    val dy2 = arrowHeadLength * sin(angleRadians - headAngleRadians).toFloat()

    // 绘制箭头头部的第一条斜线
    drawScope.drawLine(
        color = color,
        start = Offset(endX, endY),
        end = Offset(endX - dx1, endY - dy1),
        strokeWidth = 10f
    )

    // 绘制箭头头部的第二条斜线
    drawScope.drawLine(
        color = color,
        start = Offset(endX, endY),
        end = Offset(endX - dx2, endY - dy2),
        strokeWidth = 10f
    )
}
