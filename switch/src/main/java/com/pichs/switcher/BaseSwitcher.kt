package com.pichs.switcher

import android.animation.AnimatorSet
import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import kotlin.math.max
import kotlin.math.min

abstract class BaseSwitcher @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        const val SWITCHER_ANIMATION_DURATION = 800L
        const val COLOR_ANIMATION_DURATION = 300L
        const val TRANSLATE_ANIMATION_DURATION = 200L
        const val ON_CLICK_RADIUS_OFFSET = 2f
        const val BOUNCE_ANIM_AMPLITUDE_IN = 0.2
        const val BOUNCE_ANIM_AMPLITUDE_OUT = 0.15
        const val BOUNCE_ANIM_FREQUENCY_IN = 14.5
        const val BOUNCE_ANIM_FREQUENCY_OUT = 12.0
        const val STATE = "switch_state"
        const val KEY_CHECKED = "checked"
    }

    protected open var iconRadius = 0f
    protected open var iconClipRadius = 0f
    protected open var iconCollapsedWidth = 0f
    protected open var defHeight = 0
    protected open var defWidth = 0
    open var isChecked = true
        protected set

    @ColorInt
    open var onColor = 0

    @ColorInt
    open var offColor = 0
    protected val ANDROID_ATTRS_NAMESPACE = "http://schemas.android.com/apk/res/android"

    @ColorInt
    protected open var iconColor = 0

    protected open val switcherPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    protected open val iconRect = RectF(0f, 0f, 0f, 0f)
    protected open val iconClipRect = RectF(0f, 0f, 0f, 0f)
    protected open val iconPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    protected open val iconClipPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    protected open var animatorSet: AnimatorSet? = AnimatorSet()

    protected open val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    protected open var shadow: Bitmap? = null
    protected open var shadowOffset = 0f

    @ColorInt
    protected open var currentColor = 0
        set(value) {
            field = value
            switcherPaint.color = value
            iconClipPaint.color = value
        }

    protected open var switchElevation = 0f
    protected open var iconHeight = 0f

    protected open var iconProgress = 0f

    init {
        attrs?.let { retrieveAttributes(attrs, defStyleAttr) }
        this.setOnClickListener { setChecked(!isChecked) }
    }

    protected open fun retrieveAttributes(attrs: AttributeSet, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseSwitcher, defStyleAttr, 0)
        switchElevation = typedArray.getDimension(R.styleable.BaseSwitcher_android_elevation, 4f)
        onColor = typedArray.getColor(R.styleable.BaseSwitcher_xp_bgColor_switchOn, Color.GREEN)
        offColor = typedArray.getColor(R.styleable.BaseSwitcher_xp_bgColor_switchOff, Color.LTGRAY)
        iconColor = typedArray.getColor(R.styleable.BaseSwitcher_xp_iconColor_switch, Color.WHITE)
        isChecked = typedArray.getBoolean(R.styleable.BaseSwitcher_android_checked, false)
        if (!isChecked) iconProgress = 1f
        currentColor = if (isChecked) onColor else offColor
        iconPaint.color = iconColor
        typedArray.recycle()

        //get layout_width
        when (attrs.getAttributeValue(ANDROID_ATTRS_NAMESPACE, "layout_width")) {
            ViewGroup.LayoutParams.MATCH_PARENT.toString() -> {
                defWidth = ViewGroup.LayoutParams.MATCH_PARENT
            }
            ViewGroup.LayoutParams.WRAP_CONTENT.toString() -> {
                defWidth = ViewGroup.LayoutParams.WRAP_CONTENT
            }
            else -> {
                val taa = context.obtainStyledAttributes(attrs, intArrayOf(android.R.attr.layout_width))
                defWidth = taa.getDimensionPixelSize(0, context.toPx(46f))
                taa.recycle()
            }
        }

        //get layout_height
        when (attrs.getAttributeValue(ANDROID_ATTRS_NAMESPACE, "layout_height")) {
            ViewGroup.LayoutParams.MATCH_PARENT.toString() -> {
                defHeight = ViewGroup.LayoutParams.MATCH_PARENT
            }
            ViewGroup.LayoutParams.WRAP_CONTENT.toString() -> {
                defHeight = ViewGroup.LayoutParams.WRAP_CONTENT
            }
            else -> {
                val taa = context.obtainStyledAttributes(attrs, intArrayOf(android.R.attr.layout_height))
                defHeight = taa.getDimensionPixelSize(0, ViewGroup.LayoutParams.WRAP_CONTENT)
                taa.recycle()
            }
        }

        if (!isLollipopOrAbove() && switchElevation > 0f) {
            shadowPaint.colorFilter = PorterDuffColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN)
            shadowPaint.alpha = 51 // 20%
            setShadowBlurRadius(switchElevation)
            setLayerType(LAYER_TYPE_SOFTWARE, null)
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        super.onSaveInstanceState()
        return Bundle().apply {
            putBoolean(KEY_CHECKED, isChecked)
            putParcelable(STATE, super.onSaveInstanceState())
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            super.onRestoreInstanceState(state.getParcelable(STATE))
            isChecked = state.getBoolean(KEY_CHECKED)
            if (!isChecked) forceUncheck()
        }
    }

    private fun setShadowBlurRadius(elevation: Float) {
        val maxElevation = context.toPx(24f)
        switchElevation = min(25f * (elevation / maxElevation), 25f)
    }

    protected var listener: ((isChecked: Boolean) -> Unit)? = null

    /**
     * Register a callback to be invoked when the isChecked state of this switch
     * changes.
     *
     * @param listener the callback to call on isChecked state change
     */
    fun setOnCheckedChangeListener(listener: (isChecked: Boolean) -> Unit) {
        this.listener = listener
    }

    /**
     * <p>Changes the isChecked state of this switch.</p>
     *
     * @param checked true to check the switch, false to uncheck it
     * @param withAnimation use animation
     */
    abstract fun setChecked(checked: Boolean, withAnimation: Boolean = true)

    abstract fun generateShadow()

    abstract fun animateSwitch()

    abstract fun forceUncheck()
}