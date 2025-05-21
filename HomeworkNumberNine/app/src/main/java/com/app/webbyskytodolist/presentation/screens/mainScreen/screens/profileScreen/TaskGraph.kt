package com.app.webbyskytodolist.presentation.screens.mainScreen.screens.profileScreen

import android.graphics.Paint
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.webbyskytodolist.presentation.ui.theme.MainButtonColor
import com.app.webbyskytodolist.presentation.ui.theme.MainDarkColor

@Composable
fun TaskGraph(taskCounts: List<TaskCountData>) {
    val context = LocalContext.current
    val lineColor = MainButtonColor
    val gridLineColor = Color.LightGray.copy(alpha = 0.5f)
    val gradientColors = listOf(
        lineColor.copy(alpha = 0.5f),
        Color.Transparent
    )

    Box(
        modifier = Modifier.height(350.dp)
            .padding(top = 16.dp)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(MainDarkColor)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { touchPosition ->
                            val space = size.width / (taskCounts.size - 1).toFloat().coerceAtLeast(1f)
                            val maxValue = taskCounts.maxOfOrNull { it.count }?.toFloat() ?: 1f
                            val zeroY = size.height
                            val topPadding = 20.dp.toPx()

                            val points = taskCounts.mapIndexed { index, item ->
                                val x = index * space
                                val y = zeroY - ((item.count.toFloat() / maxValue) * (zeroY - topPadding))
                                Offset(x, y)
                            }

                            for (i in points.indices) {
                                val point = points[i]
                                val distance = Offset(
                                    x = touchPosition.x - point.x,
                                    y = touchPosition.y - point.y
                                ).getDistance()

                                if (distance < 32.dp.toPx()) {
                                    val day = taskCounts[i].date
                                    val count = taskCounts[i].count
                                    Toast.makeText(
                                        context,
                                        "$day: Number of tasks: $count",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    break
                                }
                            }
                        }
                    )
                }
        ) {
            val space = size.width / (taskCounts.size - 1).toFloat().coerceAtLeast(1f)
            val maxValue = taskCounts.maxOfOrNull { it.count }?.toFloat() ?: 1f
            val zeroY = size.height
            val topPadding = 20.dp.toPx()

            val points = taskCounts.mapIndexed { index, item ->
                val x = index * space
                val y = zeroY - ((item.count.toFloat() / maxValue) * (zeroY - topPadding))
                Offset(x, y)
            }

            val steps = 4
            for (i in 0..steps) {
                val ratio = i.toFloat() / steps
                val y = zeroY - ((zeroY - topPadding) * ratio)

                drawLine(
                    color = gridLineColor,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = 1.5f
                )
            }

            for (i in taskCounts.indices) {
                val x = i * space
                drawLine(
                    color = gridLineColor,
                    start = Offset(x, 0f),
                    end = Offset(x, zeroY),
                    strokeWidth = 2.5f
                )
            }

            val fillPath = Path().apply {
                moveTo(points[0].x, points[0].y)
                for (point in points) {
                    lineTo(point.x, point.y)
                }
                lineTo(points.last().x, zeroY)
                lineTo(points.first().x, zeroY)
                close()
            }

            clipPath(fillPath) {
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = gradientColors,
                        endY = zeroY - topPadding
                    ),
                    size = size
                )
            }

            drawPoints(
                points = points,
                pointMode = PointMode.Polygon,
                color = lineColor,
                strokeWidth = 4f,
                cap = StrokeCap.Round,
                pathEffect = PathEffect.cornerPathEffect(10f)
            )

            for (point in points) {
                drawCircle(
                    color = lineColor,
                    radius = 10f,
                    center = point
                )
            }

            for (i in points.indices) {
                val x = points[i].x
                val dayText = taskCounts[i].date

                drawContext.canvas.nativeCanvas.drawText(
                    dayText,
                    x,
                    zeroY + 20.dp.toPx(),
                    Paint().apply {
                        textSize = 12.sp.toPx()
                        textAlign = android.graphics.Paint.Align.CENTER
                        color = android.graphics.Color.WHITE
                    }
                )
            }
        }
    }
}

