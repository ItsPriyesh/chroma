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

import android.graphics.Color

enum class ColorMode {

    RGB {
        override val channels: List<Channel> = listOf(
                Channel(R.string.channel_red, 0, 255, Color::red),
                Channel(R.string.channel_green, 0, 255, Color::green),
                Channel(R.string.channel_blue, 0, 255, Color::blue)
        )

        override fun evaluateColor(channels: List<Channel>): Int =
                Color.rgb(channels[0].progress, channels[1].progress, channels[2].progress)
    },

    HSV {
        override val channels: List<Channel> = listOf(
                Channel(R.string.channel_hue, 0, 360, { colorToHSV(it)[0].toInt() }),
                Channel(R.string.channel_saturation, 0, 100, { (colorToHSV(it)[1] * 100).toInt() }),
                Channel(R.string.channel_value, 0, 100, { (colorToHSV(it)[2] * 100).toInt() })
        )

        override fun evaluateColor(channels: List<Channel>) =
                Color.HSVToColor(floatArrayOf(
                        (channels[0].progress).toFloat(),
                        (channels[1].progress / 100.0).toFloat(),
                        (channels[2].progress / 100.0).toFloat()
                ))

        private fun colorToHSV(color: Int) = FloatArray(3).apply { Color.colorToHSV(color, this) }
    },

    ARGB {
        override val channels: List<Channel> = listOf(
                Channel(R.string.channel_alpha, 0, 255, Color::alpha),
                Channel(R.string.channel_red, 0, 255, Color::red),
                Channel(R.string.channel_green, 0, 255, Color::green),
                Channel(R.string.channel_blue, 0, 255, Color::blue)
        )

        override fun evaluateColor(channels: List<Channel>): Int =
                Color.argb(
                        channels[0].progress,
                        channels[1].progress,
                        channels[2].progress,
                        channels[3].progress)
    };

    abstract internal val channels: List<Channel>

    abstract internal fun evaluateColor(channels: List<Channel>): Int

    internal data class Channel(val nameResourceId: Int,
                                val min: Int, val max: Int,
                                val extractor: (color: Int) -> Int,
                                var progress: Int = 0)

    internal companion object {
        fun fromOrdinal(ordinal: Int) = values().find { it.ordinal == ordinal } ?: ColorMode.RGB
    }
}