package com.bozedu.libcommon.util

import android.content.res.Resources

object ConvertUtil {
    /*****************************dp  px互转*********************************/
    fun Int.dp2px(): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (this * scale + 0.5f).toInt()
    }

    fun Int.px2dp(): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (this / scale + 0.5f).toInt()
    }

    fun Float.dp2px(): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (this * scale + 0.5f).toInt()
    }

    fun Float.px2dp(): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (this / scale + 0.5f).toInt()
    }

    /*****************************sp  px互转*********************************/

    fun Int.sp2px(): Int {
        val fontScale = Resources.getSystem().displayMetrics.scaledDensity
        return (this * fontScale + 0.5f).toInt()
    }

    fun Int.px2sp(): Int {
        val fontScale = Resources.getSystem().displayMetrics.scaledDensity
        return (this / fontScale + 0.5f).toInt()
    }

    fun Float.sp2px(): Int {
        val fontScale = Resources.getSystem().displayMetrics.scaledDensity
        return (this * fontScale + 0.5f).toInt()
    }

    fun Float.px2sp(): Int {
        val fontScale = Resources.getSystem().displayMetrics.scaledDensity
        return (this / fontScale + 0.5f).toInt()
    }
}