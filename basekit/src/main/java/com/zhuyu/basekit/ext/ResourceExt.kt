package com.zhuyu.basekit.ext

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


@ColorInt
fun Fragment.getColor(@ColorRes id: Int): Int {
    return context.getColorEx(id)
}

@ColorInt
fun View.getColor(@ColorRes id: Int): Int {
    return context.getColorEx(id)
}

@ColorInt
fun Context?.getColorEx(@ColorRes id: Int): Int {
    this ?: return Color.TRANSPARENT
    return ContextCompat.getColor(this, id)
}