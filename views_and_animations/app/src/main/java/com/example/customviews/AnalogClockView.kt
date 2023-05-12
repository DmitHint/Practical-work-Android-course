package com.example.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin


class AnalogClockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    /** height, width of the clock's view */
    private var mHeight = 0
    private var mWidth = 0

    /** numeric numbers to denote the hours */
    private val mClockHours = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)

    /** spacing and padding of the clock-hands around the clock round */
    private var mPadding = 0
    private var mNumeralSpacing = 0

    /** truncation of the heights of the clock-hands,
    hour clock-hand will be smaller comparetively to others */
    private var mHandTruncation = 0
    private var mHourHandTruncation = 0

    /** others attributes to calculate the locations of hour-points */
    private var mRadius = 0
    private var mPaint = Paint()
    private var mRect = Rect()
    private var isInit: Boolean = false // it will be true once the clock will be initialized.


    private var isRunning = false
    private var startTime: Long = 0
    private var elapsedTime: Long = 0
    private var currentTime: Long = 0
    private var timeListeners: MutableList<(TimeState) -> Unit> = mutableListOf()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        /** initialize necessary values  */
        if (!isInit) {
            mPaint = Paint()
            mHeight = height
            mWidth = width
            mPadding = mNumeralSpacing + 50 // spacing from the circle border
            val minAttr = min(mHeight, mWidth)
            mRadius = minAttr / 2 - mPadding

            // for maintaining different heights among the clock-hands
            mHandTruncation = minAttr / 20
            mHourHandTruncation = minAttr / 12
            isInit = true // set true once initialized
        }
        /** draw the canvas-color */
        canvas.drawColor(Color.DKGRAY)

        /** circle border */
        mPaint.reset()
        mPaint.color = Color.WHITE
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 4f
        mPaint.isAntiAlias = true
        canvas.drawCircle(mWidth / 2f, mHeight / 2f, mRadius + mPadding - 10f, mPaint)

        /** clock-center */
        mPaint.style = Paint.Style.FILL;
        canvas.drawCircle(
            mWidth / 2f,
            mHeight / 2f,
            12f,
            mPaint
        )  // the 03 clock hands will be rotated from this center point.

        /** border of hours */
        val fontSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            18f,
            resources.displayMetrics
        )
        mPaint.textSize = fontSize  // set font size (optional)

        mClockHours.forEach { hour ->
            val tmp = hour.toString()
            mPaint.getTextBounds(tmp, 0, tmp.length, mRect)  // for circle-wise bounding

            // find the circle-wise (x, y) position as mathematical rule
            val angle: Double = Math.PI / 6 * (hour - 3)
            val x = (mWidth / 2 + cos(angle) * mRadius - mRect.width() / 2).toFloat()
            val y = (mHeight / 2 + sin(angle) * mRadius + mRect.height() / 2).toFloat()

            canvas.drawText(hour.toString(), x, y, mPaint)
        }

        if (isRunning) {
            currentTime = System.currentTimeMillis()
            elapsedTime = currentTime - startTime
        }

        var hour = Math.floor((elapsedTime / 1000 / 60 / 60).toDouble()).toInt()
        hour = if (hour > 12) hour - 12 else hour

        val mins = elapsedTime / 1000 / 60
        val secs = elapsedTime / 1000

        drawHandLine(
            canvas,
            mins.toDouble(),
            false,
            false
        ) // draw minutes
        drawHandLine(
            canvas,
            ((hour + mins / 60) * 5).toDouble(),
            true,
            false
        ) // draw hours
        drawHandLine(
            canvas,
            secs.toDouble(),
            false,
            true
        ) // draw seconds

        timeListeners.forEach { it(TimeState(currentTime(), isRunning)) }

        /** invalidate the appearance for next representation of time  */
        postInvalidateDelayed(500)
        invalidate()
    }

    private fun drawHandLine(canvas: Canvas, moment: Double, isHour: Boolean, isSecond: Boolean) {
        val angle = Math.PI * moment / 30 - Math.PI / 2
        val handRadius =
            if (isHour) mRadius - mHandTruncation - mHourHandTruncation else mRadius - mHandTruncation

        if (isSecond) mPaint.color = Color.YELLOW
        else if (isHour) mPaint.color = Color.GREEN
        else mPaint.color = Color.WHITE

        canvas.drawLine(
            (mWidth / 2).toFloat(),
            (mHeight / 2).toFloat(),
            (mWidth / 2 + cos(angle) * handRadius).toFloat(),
            (mHeight / 2 + sin(angle) * handRadius).toFloat(),
            mPaint
        )
    }

    fun start() {
        startTime = if (startTime == 0L) {
            System.currentTimeMillis()
        } else
            System.currentTimeMillis() - elapsedTime
        isRunning = true
    }

    fun stop() {
        isRunning = false
    }

    fun reset() {
        stop()
        elapsedTime = 0
        isRunning = false
    }

    private fun currentTime(): Long {
        return elapsedTime
    }

    fun addUpdateListener(listener: (TimeState) -> Unit) {
        timeListeners.add(listener)
    }

    fun removeUpdateListener(listener: (TimeState) -> Unit) {
        timeListeners.remove(listener)
    }

}
