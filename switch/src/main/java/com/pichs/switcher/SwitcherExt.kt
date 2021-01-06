package com.pichs.switcher

import android.content.Context
import android.util.TypedValue

fun lerp(a: Float, b: Float, t: Float): Float =
        a + (b - a) * t

fun Context.toPx(value: Float): Int =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, resources.displayMetrics).toInt()

fun isLollipopOrAbove(): Boolean =
        android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP

