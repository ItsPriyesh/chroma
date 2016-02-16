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
import android.support.annotation.ColorInt
import android.support.v7.app.AlertDialog

class ChromaDialog private constructor(
    context: Context,
    @ColorInt initialColor: Int?,
    colorMode: ColorMode?,
    private var listener: ColorSelectListener?) : AlertDialog(context) {

  companion object {
    @JvmStatic fun with(context: Context): Builder = ChromaDialog.Builder(context)
  }

  class Builder internal constructor(private val context: Context) {

    @ColorInt private var initialColor: Int? = null
    private var colorMode: ColorMode? = null
    private var listener: ColorSelectListener? = null

    fun initialColor(@ColorInt initialColor: Int): Builder {
      this.initialColor = initialColor
      return this
    }

    fun colorMode(colorMode: ColorMode): Builder {
      this.colorMode = colorMode
      return this
    }

    fun onColorSelected(listener: ColorSelectListener): Builder {
      this.listener = listener
      return this
    }

    fun show(): Unit = ChromaDialog(context, initialColor, colorMode, listener).show()
  }

  interface ColorSelectListener {
    fun onColorSelected(@ColorInt color: Int)
  }

  init {
    val chromaView = ChromaView(
        initialColor ?: ChromaView.DefaultColor,
        colorMode ?: ChromaView.DefaultModel,
        context)

    setView(chromaView)
    setButton(BUTTON_NEGATIVE, context.getString(R.string.dialog_button_negative), { d, i -> })
    setButton(BUTTON_POSITIVE, context.getString(R.string.dialog_button_positive), { d, i ->
      listener?.onColorSelected(chromaView.currentColor)
    })
  }

  override fun onStop() {
    super.onStop()
    listener = null
  }
}
