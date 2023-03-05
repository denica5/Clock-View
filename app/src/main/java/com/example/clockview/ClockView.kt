package com.example.clockview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewDebug.ExportedProperty
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class ClockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {



    private var mHandler: Handler = Handler()
    private lateinit var mTimeUpdater: Runnable
    private val paintFillCircle = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.gray_background)
    }

    private val paintMarkMinutes = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        strokeWidth = 1.5f
        color = ContextCompat.getColor(context, R.color.pink_mark)
    }


    private val paintMarkHoursBlue = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        strokeWidth = 3f
        color = ContextCompat.getColor(context, R.color.blue_light)
    }

    private val paintMarkHoursPink = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        strokeWidth = 4f
        color = ContextCompat.getColor(context, R.color.pink_mark)
    }
    private val paintHandHour = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }


    private var padding = 0f
    private var seconds = 0
    private var minutes = 0f
    private var hours = 0f
    private var radius = 0f
    private var centerWidth = 0
    private var centerHeight = 0


    init {
        mTimeUpdater = object : Runnable {
            override fun run() {
                // update clock time data
                val currentTime: String =
                    SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                setClockTime(
                    currentTime.substring(0, 2).toFloat() % 12,
                    currentTime.substring(3, 5).toFloat(),
                    currentTime.substring(6, 8).toInt()
                )
                // recall update runnable after 1 sec (1000ms)
                mHandler.postDelayed(this, 1000)
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerWidth = width / 2
        centerHeight = height / 2
        radius = (min(width, height) / 2.0 * 0.8).toFloat()
        padding = radius / 75


    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawCircle(
            (centerWidth).toFloat(),
            (centerHeight).toFloat(),
            radius,
            paintFillCircle
        )
        val minuteMarkLength = radius / 30f
        repeat(60) {
            if (it % 5 != 0) {
                drawMarks(canvas, 6, paintMarkMinutes, it, padding, minuteMarkLength)

            }
        }
        val hourMarkLengthPink = radius / 15f
        Log.d("Check", "$radius")
        repeat(12) {
            if (it % 3 == 0) {
                drawMarks(canvas, 30, paintMarkHoursPink, it, padding, hourMarkLengthPink)
                drawNumbers(
                    canvas,
                    "$it",
                    30,
                    it.toFloat(),
                    hourMarkLengthPink * 3,
                    radius / 10,
                    ContextCompat.getColor(context, R.color.pink_mark)
                )
            } else {
                drawMarks(canvas, 30, paintMarkHoursBlue, it, padding, minuteMarkLength)
                drawNumbers(
                    canvas,
                    "$it",
                    30,
                    it.toFloat(),
                    hourMarkLengthPink * 3,
                    radius / 15,
                    ContextCompat.getColor(context, R.color.blue_light)
                )
            }
        }

        drawHands(
            canvas,
            radius / 2f,
            hours,
            radius / 30,
            ContextCompat.getColor(context, R.color.gray_hand),
            STEP_30
        )
        drawHands(
            canvas,
            radius / 2.6f,
            minutes,
            radius / 50,
            ContextCompat.getColor(context, R.color.gray_hand),
            STEP_6
        )
        drawHands(
            canvas,
            radius / 3.3f,
            seconds.toFloat(),
            radius / 100,
            ContextCompat.getColor(context, R.color.pink_mark),
            STEP_6
        )
    }

    private fun drawMarks(
        canvas: Canvas,
        step: Int,
        paint: Paint,
        position: Int,
        padding: Float,
        markLength: Float,

        ) {
        val coordinates = calculateMarksCoordinates(padding, markLength, step, position)
        canvas.drawLine(
            coordinates[0],
            coordinates[1],
            coordinates[2],
            coordinates[3],
            paint
        )
    }

    private fun drawHands(
        canvas: Canvas,
        handLength: Float,
        position: Float,
        width: Float,
        color: Int,
        step: Int


    ) {
        paintHandHour.color = color
        paintHandHour.strokeWidth = width
        val coordinates = calculateCoords(step, position, handLength)
        canvas.drawLine(
            centerWidth.toFloat(),
            centerHeight.toFloat(),
            coordinates[0],
            coordinates[1],
            paintHandHour
        )
    }

    private fun calculateMarksCoordinates(
        padding: Float,
        markLength: Float = 0f,
        step: Int,
        position: Int,

        ): ArrayList<Float> {
        val result = ArrayList<Float>(4)

        result.add(
            0,
            centerWidth + (radius - padding) * cos(((90 - step * position) * FROM_RAD_TO_DEGREES).toFloat())
        )
        result.add(
            1,
            centerHeight - (radius - padding) * sin(((90 - step * position) * FROM_RAD_TO_DEGREES).toFloat())
        )
        result.add(
            2,
            centerWidth + (radius - markLength - padding) * cos(
                ((90 - step * position) * FROM_RAD_TO_DEGREES).toFloat()
            )
        )
        result.add(
            3,
            centerHeight - (radius - markLength - padding) * sin(((90 - step * position) * FROM_RAD_TO_DEGREES).toFloat())
        )
        return result
    }

    private fun calculateCoords(
        step: Int,
        position: Float,
        endPosition: Float = 0f
    ): ArrayList<Float> {
        val result = ArrayList<Float>(2)
        result.add(
            0,
            centerWidth + (radius - padding - endPosition) * cos(((90 - step * position) * FROM_RAD_TO_DEGREES).toFloat())
        )
        result.add(
            1,
            centerHeight - (radius - padding - endPosition) * sin(((90 - step * position) * FROM_RAD_TO_DEGREES).toFloat())
        )
        return result
    }


    private fun drawNumbers(
        canvas: Canvas,
        number: String,
        step: Int,
        position: Float,
        endPosition: Float,
        textSize: Float,
        textColor: Int

    ) {
        val coordinates = calculateCoords(step, position, endPosition)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            textAlign = Paint.Align.CENTER
        }
        paint.textSize = textSize
        paint.color = textColor

        canvas.drawText(number, coordinates[0], coordinates[1]+radius/30, paint)
    }


    private fun setClockTime(hour: Float, minute: Float, second: Int) {
        hours = hour + (minute / 60)

        minutes = minute
        seconds = second
        invalidate()
    }


    fun startClock() {
        mHandler.post(mTimeUpdater)
    }


    fun stopClock() {
        mHandler.removeCallbacks(mTimeUpdater)
    }








    companion object {
        private const val FROM_RAD_TO_DEGREES = PI / 180
        private const val START_ANGLE = 90
        private const val STEP_6 = 6
        private const val STEP_30 = 30
    }
}