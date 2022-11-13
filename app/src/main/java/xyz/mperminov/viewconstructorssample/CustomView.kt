package xyz.mperminov.viewconstructorssample

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View

private const val TEXT_SIZE = 100f

open class CustomView : View {

    private var contentHeight: Int = 0
    private var contentWidth: Int = 0

    private val textPaint = TextPaint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
        textAlign = Paint.Align.LEFT
        textSize = TEXT_SIZE
    }

    private val textBoxPaint = Paint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
        color = Color.WHITE
    }

    var exampleString: String = ""

    var exampleTextColor: Int = Color.MAGENTA
        set(value) {
            field = value
            textPaint.color = field
        }

    var exampleDrawable: Drawable? = null

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(
        context,
        attrs,
        R.attr.defaultCustomViewStyleAttr
    ) {
        init(attrs,  R.attr.defaultCustomViewStyleAttr)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs, defStyleAttr)
    }

    private fun init(attrs: AttributeSet?, defStyleAttr: Int) {
        context.obtainStyledAttributes(
            attrs,
            R.styleable.CustomView,
            defStyleAttr,
            0
        )
            .use { styledAttrs ->
                exampleString = styledAttrs.getString(R.styleable.CustomView_exampleString).orEmpty()
                exampleTextColor = styledAttrs.getColor(R.styleable.CustomView_exampleTextColor, exampleTextColor)
                if (styledAttrs.hasValue(R.styleable.CustomView_exampleDrawable)) {
                    exampleDrawable = styledAttrs.getDrawable(R.styleable.CustomView_exampleDrawable)
                    exampleDrawable?.callback = this
                }
            }
        textPaint.color = exampleTextColor
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        val paddingRight = paddingRight
        val paddingBottom = paddingBottom
        contentWidth = w - paddingLeft - paddingRight
        contentHeight = h - paddingTop - paddingBottom
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        exampleDrawable?.let {
            it.setBounds(
                paddingLeft,
                paddingTop,
                paddingLeft + contentWidth,
                paddingTop + contentHeight
            )
            it.draw(canvas)
        }
        val textBounds = Rect()
        textPaint.getTextBounds(exampleString, 0, exampleString.length, textBounds)
        textBounds.offsetTo(
            (contentWidth - textBounds.width()) / 2,
            (contentHeight - textBounds.height()) / 2,
        )
        canvas.drawRect(textBounds, textBoxPaint)
        canvas.drawText(
            exampleString,
            ((contentWidth - textBounds.width()) / 2).toFloat(),
            ((contentHeight + textBounds.height()) / 2).toFloat(),
            textPaint
        )
    }

}