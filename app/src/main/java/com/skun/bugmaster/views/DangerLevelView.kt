package com.skun.bugmaster.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.skun.bugmaster.R


class DangerLevelView(context: Context, attrs: AttributeSet) : View(context, attrs){

    private var dangerLevel = 0;
    private val paint = Paint()


    private var size = 0

    init {
        paint.isAntiAlias = true
        setupAttributes(attrs)
    }

    private fun setupAttributes(attrs: AttributeSet?) {

        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.DangerLevelView,
            0, 0)
        dangerLevel =
            typedArray.getInt(R.styleable.DangerLevelView_dangerLevel, -1).toLong().toInt()

        typedArray.recycle()
    }

    public fun setDangerLevel(dangerLvl: Int)
    {
        dangerLevel = dangerLvl
    }

    override fun onDraw(canvas: Canvas) {
        // call the super method to keep any drawing from the parent side.
        super.onDraw(canvas)

        drawFaceBackground(canvas)
        drawDangerLevelText(canvas)
    }

    private fun drawFaceBackground(canvas: Canvas) {

        var dangerLevelColor = Color.parseColor(getResources().getStringArray(R.array.dangerColors)[dangerLevel])

        paint.color = dangerLevelColor
        paint.style = Paint.Style.FILL
        val radius = size / 2f
        canvas.drawCircle(size / 2f, size / 2f, radius, paint)
    }


    private  fun drawDangerLevelText(canvas: Canvas)
    {
        var textSize = size / (2f * 2.5f)

        val textView = TextView(context)
        textView.visibility = VISIBLE
        textView.text = dangerLevel.toString()
        textView.textSize = textSize
        textView.gravity = 10
        textView.setTextColor(Color.WHITE)


        val layout = LinearLayout(context)

        val layoutParam = LinearLayout.LayoutParams (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)

        layout.setLayoutParams(layoutParam)

        layout.gravity = 10
        layout.orientation = LinearLayout.VERTICAL
        layout.addView(textView)

        var txtMeasure = textView.measure(0, 0);
        var txtMeasureWidth = textView.getMeasuredWidth();
        var txtMeasureHeight = textView.getMeasuredHeight();
        layout.measure(canvas.width, canvas.height)
        layout.layout(0, 0, canvas.width, canvas.height)
        canvas.translate( (size / 2f)- (txtMeasureWidth/2f), (size / 2f) - (txtMeasureHeight/2f));
        layout.draw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        size = Math.min(measuredWidth, measuredHeight)

        setMeasuredDimension(size, size)
    }
}