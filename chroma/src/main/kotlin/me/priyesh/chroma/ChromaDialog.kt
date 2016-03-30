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

import android.support.v7.app.AlertDialog
import android.app.Dialog
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.v4.app.DialogFragment
import android.view.WindowManager
import me.priyesh.chroma.internal.ChromaView
import kotlin.properties.Delegates

class ChromaDialog constructor() : DialogFragment() {

    companion object {
        private val ArgInitialColor = "arg_initial_color"
        private val ArgColorModeId = "arg_color_mode_id"
        private val ArgIndicatorMode = "arg_indicator_mode"

        @JvmStatic
        private fun newInstance(@ColorInt initialColor: Int, colorMode: ColorMode, indicatorMode: IndicatorMode): ChromaDialog {
            val fragment = ChromaDialog()
            fragment.arguments = makeArgs(initialColor, colorMode, indicatorMode)
            return fragment
        }

        @JvmStatic
        private fun makeArgs(@ColorInt initialColor: Int, colorMode: ColorMode, indicatorMode: IndicatorMode): Bundle {
            val args = Bundle()
            args.putInt(ArgInitialColor, initialColor)
            args.putInt(ArgColorModeId, colorMode.ordinal)
            args.putInt(ArgIndicatorMode, indicatorMode.ordinal)
            return args
        }
    }

    class Builder {
        @ColorInt private var initialColor: Int = ChromaView.DefaultColor
        private var colorMode: ColorMode = ChromaView.DefaultModel
        private var indicatorMode: IndicatorMode = IndicatorMode.DECIMAL
        private var listener: ColorSelectListener? = null

        fun initialColor(@ColorInt initialColor: Int): Builder {
            this.initialColor = initialColor
            return this
        }

        fun colorMode(colorMode: ColorMode): Builder {
            this.colorMode = colorMode
            return this
        }

        fun indicatorMode(indicatorMode: IndicatorMode): Builder {
            this.indicatorMode = indicatorMode
            return this
        }

        fun onColorSelected(listener: ColorSelectListener): Builder {
            this.listener = listener
            return this
        }

        fun create(): ChromaDialog {
            val fragment = newInstance(initialColor, colorMode, indicatorMode)
            fragment.listener = listener
            return fragment
        }
    }

    private var listener: ColorSelectListener? = null
    private var chromaView: ChromaView by Delegates.notNull<ChromaView>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        chromaView = if (savedInstanceState == null) {
            ChromaView(
                    arguments.getInt(ArgInitialColor),
                    ColorMode.fromOrdinal(arguments.getInt(ArgColorModeId)),
                    IndicatorMode.values()[arguments.getInt(ArgIndicatorMode)],
                    context)
        } else {
            ChromaView(
                    savedInstanceState.getInt(ArgInitialColor, ChromaView.DefaultColor),
                    ColorMode.fromOrdinal(savedInstanceState.getInt(ArgColorModeId, ChromaView.DefaultModel.ordinal)),
                    IndicatorMode.values()[savedInstanceState.getInt(ArgIndicatorMode)],
                    context
            )
        }

        chromaView.enableButtonBar(object : ChromaView.ButtonBarListener {
            override fun onNegativeButtonClick() = dismiss()
            override fun onPositiveButtonClick(color: Int) {
                listener?.onColorSelected(color)
                dismiss()
            }
        })

        return AlertDialog.Builder(context, theme).setView(chromaView).create().apply {
            setOnShowListener {
                val multiplier = if(orientation(context) == ORIENTATION_LANDSCAPE) 2 else 1

                val width = resources.getDimensionPixelSize(R.dimen.chroma_dialog_width) * multiplier
                val height = WindowManager.LayoutParams.WRAP_CONTENT

                window.setLayout(width, height)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putAll(makeArgs(chromaView.currentColor, chromaView.colorMode, chromaView.indicatorMode))
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        listener = null
    }
}
