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

package me.priyesh.chroma.internal.models

import android.graphics.Color
import me.priyesh.chroma.R
import me.priyesh.chroma.internal.models.ColorModel.Channel

internal object RGB : ColorModel {

  override val channels: List<Channel> = listOf(
      Channel(R.string.channel_red, 0, 255, { color -> Color.red(color) }),
      Channel(R.string.channel_green, 0, 255, { color -> Color.green(color) }),
      Channel(R.string.channel_blue, 0, 255, { color -> Color.blue(color) })
  )

  override fun evaluateColor(channels: List<Channel>): Int =
      Color.rgb(channels[0].progress, channels[1].progress, channels[2].progress)
}