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

/**
 * Returns the hue component of a color int.
 * Based on the hidden implementation in android.graphics.Color.
 *
 * @return A hue value between 0 and 360
 */
fun hue(color: Int): Int {
  val r = color shr 16 and 0xFF
  val g = color shr 8 and 0xFF
  val b = color and 0xFF

  val V = Math.max(b, Math.max(r, g))
  val temp = Math.min(b, Math.min(r, g))

  var H: Float

  if (V == temp) {
    H = 0f
  } else {
    val vtemp = (V - temp).toFloat()
    val cr = (V - r) / vtemp
    val cg = (V - g) / vtemp
    val cb = (V - b) / vtemp

    if (r == V) {
      H = cb - cg
    } else if (g == V) {
      H = 2 + cr - cb
    } else {
      H = 4 + cg - cr
    }

    H /= 6.0f
    if (H < 0) {
      H++
    }
  }

  return (H * 360).toInt()
}

/**
 * Returns the saturation component of a color int.
 * Based on the hidden implementation in android.graphics.Color.
 *
 * @return A hue value between 0 and 100
 */
fun saturation(color: Int): Int {
  val r = color shr 16 and 0xFF
  val g = color shr 8 and 0xFF
  val b = color and 0xFF


  val V = Math.max(b, Math.max(r, g))
  val temp = Math.min(b, Math.min(r, g))

  val S: Float

  if (V == temp) {
    S = 0f
  } else {
    S = (V - temp) / V.toFloat()
  }

  return (S * 100).toInt()
}

/**
 * Returns the brightness component of a color int.
 * Based on the hidden implementation in android.graphics.Color.
 *
 * @return A hue value between 0 and 100
 */
fun brightness(color: Int): Int {
  val r = color shr 16 and 0xFF
  val g = color shr 8 and 0xFF
  val b = color and 0xFF

  val V = Math.max(b, Math.max(r, g))

  return ((V / 255.0f) * 10).toInt()
}