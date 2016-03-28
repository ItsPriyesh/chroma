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

package me.priyesh.chromasample

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_main.*
import me.priyesh.chroma.ChromaDialog
import me.priyesh.chroma.ColorMode
import me.priyesh.chroma.ColorSelectListener

class MainActivity : AppCompatActivity(), ColorSelectListener {

    private var color = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        color = savedInstanceState
                ?.let { savedInstanceState.getInt(EXTRA_COLOR) }
                ?: ContextCompat.getColor(this, R.color.colorPrimary)

        updateTextView(color)
        updateToolbar(color, color)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = darkenColor(color)
        }

        fab.setOnClickListener { showColorPickerDialog() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(EXTRA_COLOR, color)
        super.onSaveInstanceState(outState)
    }

    private fun showColorPickerDialog() {
        ChromaDialog.Builder()
                .initialColor(color)
                .colorMode(ColorMode.ARGB)
                .onColorSelected(this)
                .create()
                .show(supportFragmentManager, "dialog")
    }

  override fun onColorSelected(color: Int) {
    updateTextView(color)
    updateToolbar(color, color)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      window.statusBarColor = darkenColor(color)
    }

    this.color = color
  }

  private fun updateTextView(color: Int) {
        textView.text = String.format("#%06X", 0xFFFFFF and color)
    }

    private fun updateToolbar(oldColor: Int, newColor: Int) {
        val transition = TransitionDrawable(arrayOf(ColorDrawable(oldColor), ColorDrawable(newColor)))

        toolbar.background = transition
        transition.startTransition(300)
    }

    companion object {

        private val EXTRA_COLOR = "extra_color"

        private fun darkenColor(color: Int): Int {
            val hsv = FloatArray(3)
            Color.colorToHSV(color, hsv)
            hsv[2] *= 0.8f
            return Color.HSVToColor(hsv)
        }
    }
}
