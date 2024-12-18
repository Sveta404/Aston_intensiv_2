package com.example.rainbow

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.random.Random

class RainbowWheelView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint()
    private val wheelColors = listOf(
        Color.RED,
        Color.parseColor("#FFA500"),
        Color.YELLOW, Color.GREEN,
        Color.parseColor("#00BFFF"),
        Color.BLUE,
        Color.parseColor("#8A2BE2")
    )
    private var wheelRadius: Float = 0f
    private var isSpinning = false
    private var angle = 0f

    private var wheelListener: ((Int) -> Unit)? = null

    init {
        paint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        wheelRadius = width.coerceAtMost(height) / 2f
        val centerX = width / 2f
        val centerY = height / 2f

        for (i in wheelColors.indices) {
            paint.color = wheelColors[i]
            val startAngle = (i * 360f / wheelColors.size) + angle
            val sweepAngle = 360f / wheelColors.size
            canvas.drawArc(
                RectF(centerX - wheelRadius, centerY - wheelRadius, centerX + wheelRadius, centerY + wheelRadius),
                startAngle, sweepAngle, true, paint
            )
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            startSpinning()
            return true
        }
        return super.onTouchEvent(event)
    }

    fun startSpinning() {
        if (isSpinning) return

        isSpinning = true
        val randomRotation = Random.nextInt(720, 1080).toFloat()
        val spinDuration = 2000L

        animate().rotationBy(randomRotation).setDuration(spinDuration).withEndAction {
            isSpinning = false
            onWheelStopped()
        }.start()
    }

    private fun onWheelStopped() {
        val sectionAngle = 360f / wheelColors.size
        val wheelCenterAngle = (angle % 360 + sectionAngle / 2f) % 360
        val sectionIndex = (wheelCenterAngle / sectionAngle).toInt()

        angle = wheelCenterAngle

        wheelListener?.invoke(sectionIndex)
    }

    fun setWheelListener(listener: (Int) -> Unit) {
        wheelListener = listener
    }
}
