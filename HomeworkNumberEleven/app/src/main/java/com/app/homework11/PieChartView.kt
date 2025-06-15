package com.app.homework11

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.*

class PieChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : View(context, attrs, defStyleAttrs) {

    private var sectors: List<Pair<Any, Int>> = emptyList()
    private var colors: List<Int> = emptyList()
    private var selectedIndex: Int? = null
    private val gapAngle = 2f

    private val sectorPaint = Paint()

    private val textPaint = Paint().apply {
        color = Color.BLACK
        textSize = 40f
        textAlign = Paint.Align.CENTER
        setShadowLayer(4f, 0f, 0f, Color.WHITE)
    }

    private val holePaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL
    }

    private val rect = RectF()

    private var radius = 0f
    private var holeRadius = 0f
    private var centerX = 0f
    private var centerY = 0f

    private var sectorAngles: List<Pair<Float, Float>> = emptyList()

    fun setData(sectors: List<Pair<Any, Int>>, colors: List<Int>) {
        val sum = sectors.sumOf { it.second }
        require(sum == 100) { context.getString(R.string.the_amount_of_interest_should_be_equal_to_100) }
        require(colors.size >= sectors.size) { context.getString(R.string.the_number_of_colors_should_not_be_less_than_the_number_of_sectors) }
        for (i in 1 until colors.size) {
            require(colors[i] != colors[i - 1]) { context.getString(R.string.no_two_colors_can_be_the_same_side_by_side) }
        }
        this.sectors = sectors
        this.colors = colors
        calculateSectorAngles()
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val size = min(w, h)
        radius = size / 2f * 0.85f
        holeRadius = radius * 0.55f
        centerX = w / 2f
        centerY = h / 2f
        rect.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (sectors.isEmpty()) return

        var startAngle = -90f
        for ((i, sector) in sectors.withIndex()) {
            val sweepAngle = 360f * sector.second / 100f - gapAngle
            sectorPaint.color = if (selectedIndex == i) brightenColor(colors[i]) else colors[i]
            canvas.drawArc(rect, startAngle + gapAngle / 2, sweepAngle, true, sectorPaint)

            val angle = Math.toRadians((startAngle + gapAngle / 2 + sweepAngle / 2).toDouble())
            val centerRadius = (radius + holeRadius) / 2
            val textX = (centerX + centerRadius * cos(angle)).toFloat()
            val textY = (centerY + centerRadius * sin(angle)).toFloat() + 15f
            canvas.drawText("${sector.second}%", textX, textY, textPaint)

            startAngle += 360f * sector.second / 100f
        }

        canvas.drawCircle(centerX, centerY, holeRadius, holePaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val x = event.x - centerX
            val y = event.y - centerY
            val r = sqrt(x * x + y * y)
            if (r < holeRadius || r > radius) {
                selectedIndex = null
                invalidate()
                return true
            }
            val touchAngle = (Math.toDegrees(atan2(y, x).toDouble()) + 360 + 90) % 360
            for ((i, anglePair) in sectorAngles.withIndex()) {
                if (touchAngle >= anglePair.first && touchAngle < anglePair.second) {
                    selectedIndex = i
                    invalidate()
                    return true
                }
            }
            selectedIndex = null
            invalidate()
            return true
        }
        return super.onTouchEvent(event)
    }

    private fun calculateSectorAngles() {
        var startAngle = 0f
        sectorAngles = sectors.map {
            val sweep = 360f * it.second / 100f
            val pair = startAngle to (startAngle + sweep)
            startAngle += sweep
            pair
        }
    }

    private fun brightenColor(color: Int): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[2] = min(1f, hsv[2] + 0.2f)
        return Color.HSVToColor(hsv)
    }
}