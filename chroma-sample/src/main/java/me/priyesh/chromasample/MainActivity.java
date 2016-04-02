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

package me.priyesh.chromasample;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.priyesh.chroma.ChromaDialog;
import me.priyesh.chroma.ColorMode;
import me.priyesh.chroma.ColorSelectListener;


public class MainActivity extends AppCompatActivity {

  private static final String KEY_COLOR = "extra_color";
  private static final String KEY_COLOR_MODE = "extra_color_mode";

  @Bind(R.id.toolbar) Toolbar mToolbar;
  @Bind(R.id.text_view) TextView mColorTextView;
  @Bind(R.id.color_mode_spinner) Spinner mColorModeSpinner;

  private int mColor;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    setSupportActionBar(mToolbar);
    int statusBarHeight = statusBarHeight();
    mToolbar.setPadding(0, statusBarHeight, 0, 0);
    mToolbar.getLayoutParams().height += statusBarHeight;

    mColor = savedInstanceState != null
        ? savedInstanceState.getInt(KEY_COLOR)
        : ContextCompat.getColor(this, R.color.colorPrimary);

    ColorMode colorMode = savedInstanceState != null
        ? ColorMode.valueOf(savedInstanceState.getString(KEY_COLOR_MODE))
        : ColorMode.RGB;

    ArrayAdapter<ColorMode> adapter = new ArrayAdapter<>(
        this, android.R.layout.simple_spinner_dropdown_item, ColorMode.values());

    mColorModeSpinner.setAdapter(adapter);
    mColorModeSpinner.setSelection(adapter.getPosition(colorMode));

    updateTextView(mColor);
    updateToolbar(mColor, mColor);
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    outState.putInt(KEY_COLOR, mColor);
    outState.putString(KEY_COLOR_MODE, mColorModeSpinner.getSelectedItem().toString());
    super.onSaveInstanceState(outState);
  }

  @OnClick(R.id.fab) void onFabClick() {
    showColorPickerDialog();
  }

  private void showColorPickerDialog() {
    new ChromaDialog.Builder()
        .initialColor(mColor)
        .colorMode((ColorMode) mColorModeSpinner.getSelectedItem())
        .onColorSelected(new ColorSelectListener() {
          @Override public void onColorSelected(int color) {
            updateTextView(color);
            updateToolbar(mColor, color);
            mColor = color;
          }
        })
        .create()
        .show(getSupportFragmentManager(), "dialog");
  }

  private void updateTextView(int color) {
    mColorTextView.setText(String.format("#%06X", 0xFFFFFF & color));
  }

  private void updateToolbar(int oldColor, int newColor) {
    final TransitionDrawable transition = new TransitionDrawable(new ColorDrawable[]{
        new ColorDrawable(oldColor), new ColorDrawable(newColor)
    });

    mToolbar.setBackground(transition);
    transition.startTransition(300);
  }

  private int statusBarHeight() {
    int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
    return resId != 0 ? getResources().getDimensionPixelSize(resId) : 0;
  }
}
