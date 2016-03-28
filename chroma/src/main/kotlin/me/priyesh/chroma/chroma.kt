package me.priyesh.chroma

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

internal fun screenDimensions(context: Context): DisplayMetrics {
    val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val metrics = DisplayMetrics()
    manager.defaultDisplay.getMetrics(metrics)
    return metrics
}

internal fun orientation(context: Context) = context.resources.configuration.orientation

internal infix fun Int.percentOf(n: Int): Int = (n * (this / 100.0)).toInt()