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

package me.priyesh.chroma.models

import android.graphics.Color
import me.priyesh.chroma.R
import me.priyesh.chroma.models.ColorModel.Channel

object HSV : ColorModel {

  override val channels: List<Channel> = listOf(
          Channel(R.string.channel_hue, 0, 360,
                  { color -> colorToHSV(color)[0].toInt() }),

          Channel(R.string.channel_saturation, 0, 100,
                  { color -> (colorToHSV(color)[1] * 100).toInt() }),

          Channel(R.string.channel_value, 0, 100,
                  { color -> (colorToHSV(color)[2] * 100).toInt() })
  )

  override fun evaluateColor(channels: List<Channel>): Int =
      Color.HSVToColor(floatArrayOf(
          (channels[0].progress).toFloat(),
          (channels[1].progress / 100.0).toFloat(),
          (channels[2].progress / 100.0).toFloat()
      ))

  private fun colorToHSV(color: Int): FloatArray {
    val hsv = FloatArray(3)
    Color.colorToHSV(color, hsv)
    return hsv
  }
}