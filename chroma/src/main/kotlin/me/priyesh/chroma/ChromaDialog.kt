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
import android.support.v7.app.AlertDialog

class ChromaDialog(context: Context, listener: ColorSelectListener) : AlertDialog(context) {

  interface ColorSelectListener {
    fun onColorSelected(color: Int)
  }

  init {
    val chromaView = ChromaView(Color.RED, context)
    setView(chromaView)
    setButton(BUTTON_NEGATIVE, "Cancel", { dialog, which -> })
    setButton(BUTTON_POSITIVE, "OK", { dialog, which ->
      listener.onColorSelected(chromaView.currentColor)
    })
  }
}
