package com.example.exp3_100

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class SampleCanvas(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Set paint properties
        paint.style = Paint.Style.FILL
        paint.textSize = 40f

        // Draw Circle
        paint.color = Color.RED
        canvas.drawCircle(200f, 200f, 80f, paint)
        paint.color = Color.BLACK
        canvas.drawText("Circle", 150f, 320f, paint)

        // Draw Rectangle
        paint.color = Color.GREEN
        canvas.drawRect(350f, 150f, 500f, 400f, paint)
        paint.color = Color.BLACK
        canvas.drawText("Rectangle", 340f, 440f, paint)

        // Draw Square
        paint.color = Color.BLUE
        canvas.drawRect(100f, 450f, 250f, 600f, paint)
        paint.color = Color.BLACK
        canvas.drawText("Square", 110f, 650f, paint)

        // Draw Line
        paint.color = Color.BLACK
        paint.strokeWidth = 5f
        canvas.drawLine(400f, 500f, 400f, 600f, paint)
        canvas.drawText("Line", 360f, 650f, paint)
    }
}
