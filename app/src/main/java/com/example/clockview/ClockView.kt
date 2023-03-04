package com.example.clockview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.TextClock
import androidx.core.content.ContextCompat
import java.time.Clock
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class ClockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextClock(context, attrs, defStyleAttr) {
//     lateinit var mClock: Clock


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


//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//
//
//    }


    //    private var mSeconds = 0f
//    private var mMinutes = 0f
//    private var mHour = 0f
    private var mRadius = 0f

    //    private var mChanged = false
    private var centerWidth = 0
    private var centerHeight = 0

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRadius = (min(width, height) / 2.0 * 0.8).toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawCircle(
            (centerWidth).toFloat(),
            (centerHeight).toFloat(),
            mRadius,
            paintFillCircle
        )
        val minuteMarkLength = mRadius / 30f

        repeat(60) {
            if (it % 5 != 0) {
                drawMarks(canvas, 6, paintMarkMinutes, it, 6f, minuteMarkLength)

            }
        }
        val hourMarkLengthPink = mRadius / 15f
        repeat(12) {
            if (it % 3 == 0) {
                drawMarks(canvas, 30, paintMarkHoursPink, it, 6f, hourMarkLengthPink)
            } else {
                drawMarks(canvas, 30, paintMarkHoursBlue, it, 6f, minuteMarkLength)
            }
        }

    }

    private fun drawMarks(
        canvas: Canvas,
        step: Int,
        paint: Paint,
        position: Int,
        padding: Float,
        markLength: Float,

        ) {
        canvas.drawLine(
            centerWidth + (mRadius - padding) * cos(((90 - step * position) * FROM_RAD_TO_DEGREES).toFloat()),
            centerHeight - (mRadius - padding) * sin(((90 - step * position) * FROM_RAD_TO_DEGREES).toFloat()),
            centerWidth + (mRadius - markLength - padding) * cos(
                ((90 - step * position) * FROM_RAD_TO_DEGREES).toFloat()
            ),
            centerHeight - (mRadius - markLength - padding) * sin(
                ((90 - step * position) * FROM_RAD_TO_DEGREES).toFloat()
            ),
            paint
        )

    }

//    private fun drawNumbers(
//        canvas: Canvas,
//        number: Number,
//        padding: Float
//
//    ) {
////        canvas.drawText(number.toChar(), )
//    }

    companion object {
        private const val FROM_RAD_TO_DEGREES = PI / 180

    }
}