package com.pichs.switcher

import android.view.animation.Interpolator
import kotlin.math.cos
import kotlin.math.pow


class BounceInterpolator(
    private val amplitude: Double,
    private val frequency: Double
) : Interpolator {
    override fun getInterpolation(time: Float): Float =
        (-1 * Math.E.pow(-time / amplitude) * cos(frequency * time) + 1).toFloat()
}