package edu.arbelkilani.circularcountdown

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.res.ResourcesCompat
import kotlin.math.min
import kotlin.math.roundToInt

class CircularCountdown(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    companion object {
        private const val DEFAULT_UNSPECIFIED_SIZE = 180
        private const val DEFAULT_BORDER_COLOR = Color.BLACK
        private const val DEFAULT_BORDER_THICKNESS = 10f
        private const val DEFAULT_DISK_COLOR = Color.GRAY
        private const val DEFAULT_START_ANGLE = -90f
        private const val DEFAULT_COUNTDOWN_VALUE = 2000L
        private const val EMPTY_ANGLE = 0f
        private const val FULL_ANGLE = 360f
        private const val DEFAULT_DIRECTION = 1
        private const val DEFAULT_DISK_ALPHA = 1f
    }

    private var borderColor = DEFAULT_BORDER_COLOR
    private var borderThickness = DEFAULT_BORDER_THICKNESS

    private var diskColor = DEFAULT_DISK_COLOR
    private var diskAlpha = DEFAULT_DISK_ALPHA

    private var startAngle = DEFAULT_START_ANGLE
    private var sweepAngle = 0f
    private var direction = DEFAULT_DIRECTION

    private var duration = DEFAULT_COUNTDOWN_VALUE
    private lateinit var valueAnimator: ValueAnimator

    private val animatorListener = object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {

        }

        override fun onAnimationEnd(animation: Animator?) {
            onAnimationEnd?.invoke()
        }

        override fun onAnimationCancel(animation: Animator?) {

        }

        override fun onAnimationStart(animation: Animator?) {
            onAnimationStart?.invoke()
        }

    }

    var onAnimationStart: (() -> Unit)? = null
    var onAnimationEnd: (() -> Unit)? = null

    private var paint: Paint = Paint()
    private var size = 0

    init {
        paint.isAntiAlias = true
        setupAttributes(attrs)
        setupValueAnimator()
    }

    private fun setupValueAnimator() {
        valueAnimator = ValueAnimator.ofFloat(EMPTY_ANGLE, FULL_ANGLE)
        valueAnimator.duration = duration
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.addUpdateListener {
            sweepAngle = it.animatedValue as Float
            invalidate()
        }

        valueAnimator.addListener(animatorListener)

    }

    private fun setupAttributes(attrs: AttributeSet?) {
        attrs?.apply {
            val typedArray =
                context.theme.obtainStyledAttributes(attrs, R.styleable.CircularCountdown, 0, 0)

            borderColor = typedArray.getColor(
                R.styleable.CircularCountdown_border_color,
                DEFAULT_BORDER_COLOR
            )

            borderThickness = typedArray.getDimension(
                R.styleable.CircularCountdown_border_thickness,
                DEFAULT_BORDER_THICKNESS
            )

            diskColor = typedArray.getColor(
                R.styleable.CircularCountdown_disk_color,
                DEFAULT_DISK_COLOR
            )

            diskAlpha = typedArray.getFloat(
                R.styleable.CircularCountdown_disk_alpha,
                DEFAULT_DISK_ALPHA
            )

            startAngle =
                typedArray.getInt(
                    R.styleable.CircularCountdown_start_angle,
                    DEFAULT_START_ANGLE.toInt()
                )
                    .toFloat()

            direction = typedArray.getInt(
                R.styleable.CircularCountdown_direction,
                DEFAULT_DIRECTION
            )
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        when (MeasureSpec.getMode(widthMeasureSpec)) {
            MeasureSpec.UNSPECIFIED, MeasureSpec.AT_MOST -> size = DEFAULT_UNSPECIFIED_SIZE.px
            MeasureSpec.EXACTLY -> size = min(measuredWidth, measuredHeight)
        }
        setMeasuredDimension(size, size)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val center = size / 2f

        drawDisk(canvas, center)
        drawBorder(canvas, center)

    }

    private fun drawBorder(canvas: Canvas?, center: Float) {

        paint.style = Paint.Style.STROKE
        paint.color = borderColor
        paint.strokeWidth = borderThickness

        canvas?.apply {
            val radius = center - paint.strokeWidth
            canvas.drawCircle(center, center, radius, paint)
        }
    }

    private fun drawDisk(canvas: Canvas?, center: Float) {

        paint.style = Paint.Style.FILL
        paint.color = diskColor
        paint.alpha = (255 * diskAlpha).roundToInt()

        val radius = center - borderThickness
        canvas?.apply {
            val rectF = RectF(center - radius, center - radius, center + radius, center + radius)
            canvas.drawArc(rectF, startAngle, sweepAngle * direction, true, paint)
        }
    }

    fun setDuration(value: Long) {
        duration = value
        invalidate()
    }

    fun start() {
        valueAnimator.start()
    }

    fun startDelay(delay: Long) {
        valueAnimator.startDelay = delay
        valueAnimator.start()
    }

    fun setBorderColor(borderColor: Int) {
        this.borderColor = ResourcesCompat.getColor(context.resources, borderColor, null)
        invalidate()
    }

    fun setDiskColor(diskColor: Int) {
        this.diskColor = ResourcesCompat.getColor(context.resources, diskColor, null)
        invalidate()
    }

    val Int.px: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()
}