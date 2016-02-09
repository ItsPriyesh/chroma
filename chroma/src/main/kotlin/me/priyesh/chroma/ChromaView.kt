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

  var currentColor = initialColor

  init {
    orientation = VERTICAL
    clipToPadding = false

    val colorView = ColorView(currentColor, context)
    addView(colorView)

    val channelViews = listOf(
        ChannelView(R.string.channel_red, Color.red(currentColor), context),
        ChannelView(R.string.channel_green, Color.green(currentColor), context),
        ChannelView(R.string.channel_blue, Color.blue(currentColor), context)
    )

    val seekbarChangeListener: () -> Unit = {
      val currentValues = channelViews.map { it.currentProgress }
      currentColor = Color.rgb(currentValues[0], currentValues[1], currentValues[2])
      colorView.setColor(currentColor)
    }

    channelViews.forEach { it ->
      addView(it)

      val layoutParams = it.layoutParams as LinearLayout.LayoutParams
      layoutParams.topMargin = resources.getDimensionPixelSize(R.dimen.channel_view_margin_top)
      layoutParams.bottomMargin = resources.getDimensionPixelSize(R.dimen.channel_view_margin_bottom)

      it.registerListener(seekbarChangeListener)
    }
  }

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

  private class ChannelView(
      private val labelResourceId: Int,
      private val initialProgress: Int,
      context: Context) : RelativeLayout(context) {

    var currentProgress = initialProgress
    var listener: (() -> Unit)? = null

    init {
      if (initialProgress < 0 || initialProgress > 255) {
        throw IllegalArgumentException("Initial progress must be between 0 and 255")
      }

      val rootView = inflate(context, R.layout.channel_row, this)
      bindViews(rootView)
    }

    private fun bindViews(root: View): Unit {
      (root.findViewById(R.id.label) as TextView).text = context.getString(labelResourceId)

      val progressView = root.findViewById(R.id.progress_text) as TextView
      progressView.text = initialProgress.toString()

      val seekbar = root.findViewById(R.id.seekbar) as SeekBar
      seekbar.progress = initialProgress
      seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onStartTrackingTouch(seekbar: SeekBar?) { }

        override fun onStopTrackingTouch(seekbar: SeekBar?) { }

        override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
          currentProgress = progress
          progressView.text = currentProgress.toString()
          listener?.invoke()
        }
      })
    }

    fun registerListener(listener: () -> Unit): Unit {
      this.listener = listener
    }
  }
}