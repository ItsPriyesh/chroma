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
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import me.priyesh.chroma.ChromaDialog;
import me.priyesh.chroma.ColorMode;

public class MainActivity extends AppCompatActivity {

  private Toolbar mToolbar;
  private TextView mColorTextView;
  private int mColor;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mToolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(mToolbar);

    mColor = ContextCompat.getColor(this, R.color.colorPrimary);

    mColorTextView = (TextView) findViewById(R.id.text_view);
    updateTextView(mColor);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        showColorPickerDialog();
      }
    });
  }

  private void showColorPickerDialog() {
    ChromaDialog.with(this)
        .initialColor(mColor)
        .colorMode(ColorMode.RGB)
        .onColorSelected(new ChromaDialog.ColorSelectListener() {
          @Override public void onColorSelected(int color) {
            updateTextView(color);
            animateToolbarColor(mColor, color);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
              getWindow().setStatusBarColor(darkenColor(color));
            }
            mColor = color;
          }
        }).create().show();
  }

  private void updateTextView(int color) {
    mColorTextView.setText(String.format("#%06X", 0xFFFFFF & color));
  }

  private void animateToolbarColor(int start, int end) {
    final TransitionDrawable transition = new TransitionDrawable(new ColorDrawable[]{
        new ColorDrawable(start), new ColorDrawable(end)
    });

    mToolbar.setBackground(transition);
    transition.startTransition(300);
  }

  private int darkenColor(int color) {
    float[] hsv = new float[3];
    Color.colorToHSV(color, hsv);
    hsv[2] *= 0.8f;
    return Color.HSVToColor(hsv);
  }
}
