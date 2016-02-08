/*
 * Copyright 2016 Priyesh Patel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.priyesh.chroma

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.TextView

class ChromaView(initialColor: Int, context: Context) : LinearLayout(context) {

  private class ColorView(initialColor: Int, context: Context) : View(context) {
    init {
      setColor(initialColor)
      layoutParams = LayoutParams(
          LayoutParams.MATCH_PARENT,
          resources.getDimensionPixelSize(R.dimen.color_view_height)
      )
    }

    fun setColor(color: Int): Unit = setBackgroundColor(color)
  }

  private class ChannelView(val name: String, val startingValue: Int, context: Context) : RelativeLayout(context) {

    var currentValue = startingValue
    var listener: (() -> Unit)? = null

    init {
      if (startingValue < 0 || startingValue > 255) {
        throw IllegalArgumentException("Starting value must be between 0 and 255")
      }

      val rootView = inflate(context, R.layout.channel_row, null)
      bindViews(rootView)
    }

    private fun bindViews(root: View): Unit {
      (root.findViewById(R.id.label) as TextView).text = name

      val progressView = root.findViewById(R.id.progress_text) as TextView
      progressView.text = startingValue.toString()

      (root.findViewById(R.id.seekbar) as SeekBar).setOnSeekBarChangeListener(
          object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekbar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekbar: SeekBar?) {
            }

            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
              currentValue = progress
              progressView.text = currentValue.toString()
              listener?.invoke()
            }
          })
    }

    fun registerListener(listener: () -> Unit): Unit {
      this.listener = listener
    }
  }

  var currentColor = initialColor

  init {
    orientation = VERTICAL
    clipToPadding = false

    val colorView = ColorView(initialColor, context)
    addView(colorView)

    val channelViews = listOf(
        ChannelView("R", 0, context),
        ChannelView("G", 0, context),
        ChannelView("B", 0, context)
    )

    val seekbarChangeListener: () -> Unit = {
      val currentValues = channelViews.map { it.currentValue }
      currentColor = Color.rgb(currentValues[0], currentValues[1], currentValues[2])
      colorView.setColor(currentColor)
    }

    channelViews.forEach { it ->
      it.registerListener(seekbarChangeListener)
      addView(it)
    }
  }

  fun getColor(): Int = currentColor
}