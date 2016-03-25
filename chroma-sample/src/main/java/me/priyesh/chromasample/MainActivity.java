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

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.priyesh.chroma.ChromaDialog;
import me.priyesh.chroma.ColorMode;
import me.priyesh.chroma.ColorSelectListener;

public class MainActivity extends AppCompatActivity {

  private static final String EXTRA_COLOR = "extra_color";

  @Bind(R.id.toolbar) Toolbar mToolbar;
  @Bind(R.id.text_view) TextView mColorTextView;

  private int mColor;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    setSupportActionBar(mToolbar);

    mColor = savedInstanceState != null
        ? savedInstanceState.getInt(EXTRA_COLOR)
        : ContextCompat.getColor(this, R.color.colorPrimary);

    updateTextView(mColor);
    updateToolbar(mColor, mColor);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      getWindow().setStatusBarColor(darkenColor(mColor));
    }
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    outState.putInt(EXTRA_COLOR, mColor);
    super.onSaveInstanceState(outState);
  }

  @OnClick(R.id.fab) void onFabClick() {
    showColorPickerDialog();
  }

  private void showColorPickerDialog() {
    new ChromaDialog.Builder()
        .initialColor(mColor)
        .colorMode(ColorMode.RGB)
        .onColorSelected(new ColorSelectListener() {
          @Override public void onColorSelected(int color) {
            updateTextView(color);
            updateToolbar(mColor, color);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
              getWindow().setStatusBarColor(darkenColor(color));
            }
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

  private static int darkenColor(int color) {
    float[] hsv = new float[3];
    Color.colorToHSV(color, hsv);
    hsv[2] *= 0.8f;
    return Color.HSVToColor(hsv);
  }
}
